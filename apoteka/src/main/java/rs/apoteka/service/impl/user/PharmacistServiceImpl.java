package rs.apoteka.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.auth.Role;
import rs.apoteka.entity.auth.RoleType;
import rs.apoteka.entity.auth.User;
import rs.apoteka.entity.business.Pharmacy;
import rs.apoteka.entity.user.Pharmacist;
import rs.apoteka.repository.user.PharmacistRepository;
import rs.apoteka.service.intf.auth.UserService;
import rs.apoteka.service.intf.business.PharmacyService;
import rs.apoteka.service.intf.user.PharmacistService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
