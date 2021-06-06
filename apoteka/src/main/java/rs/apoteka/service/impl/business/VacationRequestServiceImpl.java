package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.VacationRequest;
import rs.apoteka.entity.user.Dermatologist;
import rs.apoteka.entity.user.Pharmacist;
import rs.apoteka.entity.user.PharmacyAdmin;
import rs.apoteka.repository.business.VacationRequestRepository;
import rs.apoteka.service.intf.auth.AuthenticationService;
import rs.apoteka.service.intf.auth.UserService;
import rs.apoteka.service.intf.business.VacationRequestService;
import rs.apoteka.service.intf.user.DermatologistService;
import rs.apoteka.service.intf.user.PharmacistService;
import rs.apoteka.service.intf.user.PharmacyAdminService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VacationRequestServiceImpl implements VacationRequestService {
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    private VacationRequestRepository vacationRequestRepository;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private DermatologistService dermatologistService;
    @Autowired
    private PharmacistService pharmacistService;
    @Autowired
    private PharmacyAdminService pharmacyAdminService;
    @Autowired
    private UserService userService;

    @Override
    public List<VacationRequest> findAll() {
        if (authenticationService.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PHARMACIST"))) {
            Pharmacist pharmacist = pharmacistService.findByUsername(authenticationService.getUsername());
            return vacationRequestRepository.findAll().stream().filter(v -> v.getEmployeeID().equals(pharmacist.getId())).collect(Collectors.toList());
        } else if (authenticationService.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_DERMATOLOGIST"))) {
            Dermatologist dermatologist = dermatologistService.findByUsername(authenticationService.getUsername());
            return vacationRequestRepository.findAll().stream().filter(v -> v.getEmployeeID().equals(dermatologist.getId())).collect(Collectors.toList());
        } else if (authenticationService.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PHARMACY_ADMIN"))) {
            PharmacyAdmin admin = pharmacyAdminService.findByUsername(authenticationService.getUsername());
            return vacationRequestRepository.findAll().stream().filter(v -> v.getPharmacy().getId().equals(admin.getPharmacy().getId())).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    @Override
    public List<VacationRequest> findAllParametrized(Long id, LocalDateTime startDate, LocalDateTime endDate, Long pharmacyID,
                                                     Long employeeID, Boolean accepted) {
        List<VacationRequest> vacationRequests = findAll();
        if (id != null) {
            vacationRequests.removeIf(p -> !p.getId().equals(id));
        }
        if (pharmacyID != null) {
            vacationRequests.removeIf(p -> !p.getPharmacy().getId().equals(pharmacyID));
        }
        if (employeeID != null) {
            vacationRequests.removeIf(p -> !p.getEmployeeID().equals(employeeID));
        }
        if (accepted != null) {
            vacationRequests.removeIf(p -> !p.getAccepted().equals(accepted));
        }
        if (startDate != null) {
            vacationRequests.removeIf(p -> p.getVacationStart().isBefore(startDate));
        }
        if (endDate != null) {
            vacationRequests.removeIf(p -> p.getVacationEnd().isAfter(endDate));
        }
        return vacationRequests;
    }

    @Override
    public VacationRequest getOne(Long id) {
        return vacationRequestRepository.getOne(id);
    }

    @Override
    public VacationRequest create(VacationRequest vacationRequest) throws Exception {
        if (!checkDates(vacationRequest)) {
            throw new Exception("Datumi se ne poklapaju!");
        }
        Pharmacist pharmacist;
        Dermatologist dermatologist;
        if (authenticationService.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PHARMACIST"))) {
            pharmacist = pharmacistService.findByUsername(authenticationService.getUsername());
            vacationRequest.setEmployeeID(pharmacist.getId());
            vacationRequest.setPharmacy(pharmacist.getPharmacy());
        } else if (authenticationService.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_DERMATOLOGIST"))) {
            dermatologist = dermatologistService.findByUsername(authenticationService.getUsername());
            vacationRequest.setEmployeeID(dermatologist.getId());
        }
        vacationRequest.setAccepted(false);
        vacationRequest.setRejected(false);
        return vacationRequestRepository.save(vacationRequest);
    }

    @Override
    public VacationRequest update(VacationRequest vacationRequest) throws Exception {
        if (!checkDates(vacationRequest)) {
            throw new Exception("Datumi se ne poklapaju!");
        }
        if (!authenticationService.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PHARMACY_ADMIN")) && vacationRequest.getAccepted()) {
            throw new Exception("Jedino administrator apoteke prihvata zahteve za odmor/odsustvo!");
        }
        return vacationRequestRepository.save(vacationRequest);
    }

    @Override
    public VacationRequest accept(VacationRequest vacationRequest) throws Exception {
        if (!checkDates(vacationRequest)) {
            throw new Exception("Datumi se ne poklapaju!");
        }
        if (!authenticationService.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PHARMACY_ADMIN"))) {
            throw new Exception("Jedino administrator apoteke prihvata zahteve za odmor/odsustvo!");
        }
        vacationRequest.setAccepted(true);
        vacationRequest.setRejected(false);
        String email = userService.getOne(vacationRequest.getId()).getUsername();
        String name = userService.getOne(vacationRequest.getId()).getForename();
        sendAcceptedEmail(vacationRequest, email, name);
        return update(vacationRequest);
    }

    @Override
    public VacationRequest reject(VacationRequest vacationRequest) throws Exception {
        if (!checkDates(vacationRequest)) {
            throw new Exception("Datumi se ne poklapaju!");
        }
        if (!authenticationService.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PHARMACY_ADMIN"))) {
            throw new Exception("Jedino administrator apoteke prihvata zahteve za odmor/odsustvo!");
        }
        vacationRequest.setAccepted(false);
        vacationRequest.setRejected(true);
        String email = userService.getOne(vacationRequest.getId()).getUsername();
        String name = userService.getOne(vacationRequest.getId()).getForename();
        sendAcceptedEmail(vacationRequest, email, name);
        return update(vacationRequest);
    }

    private void sendAcceptedEmail(VacationRequest request, String username, String name) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(username);
        email.setSubject("Status zahteva za godišnjim odmorom ID=" + request.getId());
        email.setText("Poštovani/a " + name + ",\n\n" +
                "Obaveštavamo vas da je vaš zahtev za godišnjim odmorom ID=" + request.getId() + " prihvaćen.\n\n" +
                "Srdačan pozdrav,\n\n" +
                "ISA");
        mailSender.send(email);
    }

    private void sendRejectedEmail(VacationRequest request, String username, String name) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(username);
        email.setSubject("Status zahteva za godišnjim odmorom ID=" + request.getId());
        email.setText("Poštovani/a " + name + ",\n\n" +
                "Obaveštavamo vas da vaš zahtev za godišnjim odmorom ID=" + request.getId() + " nije prihvaćen.\n\n" +
                "Razlog koji je administrator naveo:\n\n" +
                request.getRejectionReason() + "\n\n" +
                "Srdačan pozdrav,\n\n" +
                "ISA");
        mailSender.send(email);
    }

    private Boolean checkDates(VacationRequest vacationRequest) {
        return vacationRequest.getVacationStart().isBefore(vacationRequest.getVacationEnd()) && vacationRequest.getVacationStart().isAfter(LocalDateTime.now());
    }

    @Override
    public Boolean delete(Long id) {
        vacationRequestRepository.deleteById(id);
        return true;
    }
}
