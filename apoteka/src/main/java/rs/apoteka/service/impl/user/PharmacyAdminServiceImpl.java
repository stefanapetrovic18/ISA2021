package rs.apoteka.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.user.Pharmacist;
import rs.apoteka.entity.user.PharmacyAdmin;
import rs.apoteka.repository.user.PharmacyAdminRepository;
import rs.apoteka.service.intf.user.PharmacyAdminService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PharmacyAdminServiceImpl implements PharmacyAdminService {
    @Autowired
    private PharmacyAdminRepository pharmacyAdminRepository;

    @Override
    public List<PharmacyAdmin> findAll() {
        return pharmacyAdminRepository.findAll();
    }

    @Override
    public List<PharmacyAdmin> findAllParametrized(Long id, Long pharmacyID) {
        List<PharmacyAdmin> pharmacyAdmins = findAll();
        if (id != null) {
            pharmacyAdmins.removeIf(p -> !p.getId().equals(id));
        }
        if (pharmacyID != null) {
            pharmacyAdmins.removeIf(p -> !p.getPharmacy().getId().equals(pharmacyID));
        }
        return pharmacyAdmins;
    }

    @Override
    public PharmacyAdmin getOne(Long id) {
        return pharmacyAdminRepository.getOne(id);
    }

    @Override
    public PharmacyAdmin create(PharmacyAdmin pharmacyAdmin) {
        return pharmacyAdminRepository.save(pharmacyAdmin);
    }

    @Override
    public PharmacyAdmin update(PharmacyAdmin pharmacyAdmin) {
        return pharmacyAdminRepository.save(pharmacyAdmin);
    }

    @Override
    public Boolean delete(Long id) {
        pharmacyAdminRepository.deleteById(id);
        return true;
    }
}
