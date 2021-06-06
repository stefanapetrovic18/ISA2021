package rs.apoteka.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Pharmacy;
import rs.apoteka.entity.user.Dermatologist;
import rs.apoteka.entity.user.Pharmacist;
import rs.apoteka.entity.user.PharmacyAdmin;
import rs.apoteka.exception.UserNotFoundException;
import rs.apoteka.repository.user.DermatologistRepository;
import rs.apoteka.service.intf.auth.AuthenticationService;
import rs.apoteka.service.intf.business.PharmacyService;
import rs.apoteka.service.intf.business.WorkingHoursService;
import rs.apoteka.service.intf.user.DermatologistService;
import rs.apoteka.service.intf.user.PharmacyAdminService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DermatologistServiceImpl implements DermatologistService {
    @Autowired
    private DermatologistRepository dermatologistRepository;
    @Autowired
    private PharmacyService pharmacyService;
    @Autowired
    private WorkingHoursService workingHoursService;
    @Autowired
    private PharmacyAdminService pharmacyAdminService;
    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public List<Dermatologist> findAll() {
        return dermatologistRepository.findAll();
    }

    @Override
    public List<Dermatologist> findAllByPharmaciesContaining(Long pharmacyID) {
        Pharmacy pharmacy = pharmacyService.getOne(pharmacyID);
        if (pharmacy == null) {
            return null;
        }
        return dermatologistRepository.findAllByPharmaciesContaining(pharmacy);
    }

    @Override
    public Dermatologist findByUsername(String username) {
        return dermatologistRepository.findByUsername(username);
    }

    @Override
    public List<Dermatologist> findAllNotEmployedInPharmacy(Long pharmacyID) {
        return findAll().stream().filter(d -> d.getPharmacies().removeIf(ph -> ph.getId().equals(pharmacyID))).collect(Collectors.toList());
    }

    @Override
    public List<Dermatologist> findAllParametrized(Long id, Long workingHoursID, Long pharmacyID, LocalDateTime vacationStart, LocalDateTime vacationEnd,
                                                   Long examinationID, Double rating, Double ratingFrom, Double ratingTo) {
        List<Dermatologist> dermatologists = findAll();
        if (id != null) {
            dermatologists.removeIf(p -> !p.getId().equals(id));
        }
        if (pharmacyID != null) {
            dermatologists = dermatologists.stream().filter(d -> d.getPharmacies().removeIf(ph -> !ph.getId().equals(pharmacyID))).collect(Collectors.toList());
        }
        if (examinationID != null) {
            dermatologists = dermatologists.stream().filter(d -> d.getAppointments().removeIf(ph -> !ph.getId().equals(examinationID))).collect(Collectors.toList());
        }
        if (workingHoursID != null) {
            dermatologists = dermatologists.stream().filter(d -> d.getWorkingHours().removeIf(ph -> !ph.getId().equals(workingHoursID))).collect(Collectors.toList());
        }
        if (rating != null) {
            dermatologists.removeIf(p -> !p.getRating().equals(rating));
        }
        if (ratingFrom != null) {
            dermatologists.removeIf(p -> p.getRating() < ratingFrom);
        }
        if (ratingTo != null) {
            dermatologists.removeIf(p -> p.getRating() > ratingTo);
        }
        if (vacationStart != null) {
            dermatologists.removeIf(p -> p.getVacationStart().isBefore(vacationStart));
        }
        if (vacationEnd != null) {
            dermatologists.removeIf(p -> p.getVacationStart().isAfter(vacationEnd));
        }
        return dermatologists;
    }

    @Override
    public Dermatologist getOne(Long id) {
        return dermatologistRepository.getOne(id);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
    public Dermatologist create(Dermatologist dermatologist) {
        dermatologist.setEnabled(true);
        dermatologist.setPasswordChanged(false);
        dermatologist.setValidated(false);
        dermatologist.setPassword(new BCryptPasswordEncoder().encode(dermatologist.getPassword()));
//        dermatologist.getWorkingHours().forEach(wh -> workingHoursService.create(wh));
        Dermatologist d = dermatologistRepository.save(dermatologist);
        if (d.getWorkingHours() != null) {
            d.getWorkingHours().forEach(wh -> wh.setEmployeeID(d.getId()));
            d.getWorkingHours().forEach(wh -> workingHoursService.update(wh));
        }
        return d;
    }

    @Override
    public Dermatologist fire(Dermatologist dermatologist) throws UserNotFoundException {
        PharmacyAdmin admin = pharmacyAdminService.findByUsername(authenticationService.getUsername());
        if (admin.getPharmacy() == null) {
            throw new UserNotFoundException();
        }
        Dermatologist d = findByUsername(dermatologist.getUsername());
        d.getPharmacies().removeIf(p -> p.getId().equals(admin.getPharmacy().getId()));
        d.getPharmacies().forEach(p -> {
            if (p.getId().equals(admin.getPharmacy().getId())) {
                admin.getPharmacy().getDermatologists().removeIf(dt -> dt.getId().equals(d.getId()));
                pharmacyService.update(admin.getPharmacy());
            }
        });
        if (d.getPharmacies().isEmpty()) {
            d.setRoles(null);
        }
        return dermatologistRepository.save(d);
    }

    @Override
    public Dermatologist update(Dermatologist dermatologist) {
        return dermatologistRepository.save(dermatologist);
    }

    @Override
    public Boolean delete(Long id) {
        dermatologistRepository.deleteById(id);
        return true;
    }
}
