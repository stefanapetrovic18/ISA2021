package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.VacationRequest;
import rs.apoteka.entity.user.Dermatologist;
import rs.apoteka.entity.user.Pharmacist;
import rs.apoteka.entity.user.PharmacyAdmin;
import rs.apoteka.repository.business.VacationRequestRepository;
import rs.apoteka.service.intf.auth.AuthenticationService;
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
    private VacationRequestRepository vacationRequestRepository;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private DermatologistService dermatologistService;
    @Autowired
    private PharmacistService pharmacistService;
    @Autowired
    private PharmacyAdminService pharmacyAdminService;

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
    public VacationRequest create(VacationRequest vacationRequest) {
        if (!checkDates(vacationRequest)) {
            return null;
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
            return null;
        }
        if (!authenticationService.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PHARMACY_ADMIN")) && vacationRequest.getAccepted()) {
            throw new Exception("Jedino administrator apoteke prihvata zahteve za odmor/odsustvo!");
        }
        return vacationRequestRepository.save(vacationRequest);
    }

    @Override
    public VacationRequest accept(VacationRequest vacationRequest) throws Exception {
        if (!checkDates(vacationRequest)) {
            return null;
        }
        if (!authenticationService.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PHARMACY_ADMIN"))) {
            throw new Exception("Jedino administrator apoteke prihvata zahteve za odmor/odsustvo!");
        }
        vacationRequest.setAccepted(true);
        vacationRequest.setRejected(false);
        return update(vacationRequest);
    }

    @Override
    public VacationRequest reject(VacationRequest vacationRequest) throws Exception {
        if (!checkDates(vacationRequest)) {
            return null;
        }
        if (!authenticationService.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PHARMACY_ADMIN"))) {
            throw new Exception("Jedino administrator apoteke prihvata zahteve za odmor/odsustvo!");
        }
        vacationRequest.setAccepted(false);
        vacationRequest.setRejected(true);
        return update(vacationRequest);
    }

    private Boolean checkDates(VacationRequest vacationRequest) {
        return vacationRequest.getVacationStart().isBefore(vacationRequest.getVacationEnd());
    }

    @Override
    public Boolean delete(Long id) {
        vacationRequestRepository.deleteById(id);
        return true;
    }
}
