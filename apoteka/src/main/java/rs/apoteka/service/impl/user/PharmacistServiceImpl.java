package rs.apoteka.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.auth.Role;
import rs.apoteka.entity.auth.RoleType;
import rs.apoteka.entity.auth.User;
import rs.apoteka.entity.user.Pharmacist;
import rs.apoteka.repository.user.PharmacistRepository;
import rs.apoteka.service.intf.auth.UserService;
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
        Pharmacist newPharmacist;
        if (user == null) {
            newPharmacist = pharmacist;
        } else {
            newPharmacist = new Pharmacist(user);
        }
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(RoleType.ROLE_PHARMACIST));
        newPharmacist.setRoles(roles);
//        userService.delete(user.getId());
//        newPharmacist = new Pharmacist(user);
        newPharmacist.setPharmacy(pharmacist.getPharmacy());
        return pharmacistRepository.save(newPharmacist);
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
