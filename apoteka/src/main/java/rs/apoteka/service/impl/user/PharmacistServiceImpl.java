package rs.apoteka.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.auth.Role;
import rs.apoteka.entity.auth.RoleType;
import rs.apoteka.entity.auth.User;
import rs.apoteka.entity.business.Pharmacy;
import rs.apoteka.entity.user.Dermatologist;
import rs.apoteka.entity.user.Pharmacist;
import rs.apoteka.repository.user.PharmacistRepository;
import rs.apoteka.service.intf.auth.UserService;
import rs.apoteka.service.intf.business.PharmacyService;
import rs.apoteka.service.intf.user.PharmacistService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PharmacistServiceImpl implements PharmacistService {
    @Autowired
    private PharmacistRepository pharmacistRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PharmacyService pharmacyService;

    @Override
    public List<Pharmacist> findAll() {
        return pharmacistRepository.findAll();
    }

    @Override
    public List<Pharmacist> findAllUnemployed() {
        return findAll().stream().filter(p -> p.getPharmacy() == null).collect(Collectors.toList());
    }

    @Override
    public List<Pharmacist> findAllParametrized(Long id, Long workingHoursID, Long pharmacyID, LocalDateTime vacationStart, LocalDateTime vacationEnd,
                                                   Long consultationID, Double rating, Double ratingFrom, Double ratingTo) {
        List<Pharmacist> pharmacists = findAll();
        if (id != null) {
            pharmacists.removeIf(p -> !p.getId().equals(id));
        }
        if (pharmacyID != null) {
            pharmacists.removeIf(p -> !p.getPharmacy().getId().equals(pharmacyID));
        }
        if (consultationID != null) {
            pharmacists = pharmacists.stream().filter(d -> d.getConsultations().removeIf(ph -> !ph.getId().equals(consultationID))).collect(Collectors.toList());
        }
        if (workingHoursID != null) {
            pharmacists = pharmacists.stream().filter(d -> d.getWorkingHours().removeIf(ph -> !ph.getId().equals(workingHoursID))).collect(Collectors.toList());
        }
        if (rating != null) {
            pharmacists.removeIf(p -> !p.getRating().equals(rating));
        }
        if (ratingFrom != null) {
            pharmacists.removeIf(p -> p.getRating() < ratingFrom);
        }
        if (ratingTo != null) {
            pharmacists.removeIf(p -> p.getRating() > ratingTo);
        }
        if (vacationStart != null) {
            pharmacists.removeIf(p -> p.getVacationStart().isBefore(vacationStart));
        }
        if (vacationEnd != null) {
            pharmacists.removeIf(p -> p.getVacationStart().isAfter(vacationEnd));
        }
        return pharmacists;
    }

    @Override
    public Pharmacist getOne(Long id) {
        return pharmacistRepository.getOne(id);
    }

    @Override
    public Pharmacist create(Pharmacist pharmacist) throws Exception {
        User user = userService.findByUsername(pharmacist.getUsername());
        if (user != null) {
            throw new Exception("Korisnik sa ovom email adresom postoji!");
        }
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(RoleType.ROLE_PHARMACIST));
        pharmacist.setRoles(roles);
        pharmacist.setValidated(false);
        pharmacist.setEnabled(true);
        pharmacist.setPasswordChanged(false);
        pharmacist.setRating(0.0);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        pharmacist.setPassword(encoder.encode(pharmacist.getPassword()));
        Pharmacy pharmacy = pharmacyService.getOne(pharmacist.getPharmacy().getId());
        if (pharmacy != null) {
            pharmacist.setPharmacy(pharmacy);
            pharmacy.getPharmacists().add(pharmacist);
            pharmacyService.update(pharmacy);
        }
        return pharmacistRepository.save(pharmacist);
    }

    @Override
    public Pharmacist update(Pharmacist pharmacist) {
        return pharmacistRepository.save(pharmacist);
    }

    @Override
    public Boolean delete(Long id) {
        pharmacistRepository.deleteById(id);
        return true;
    }
}
