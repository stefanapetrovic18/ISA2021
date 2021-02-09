package rs.apoteka.service.intf.user;

import rs.apoteka.entity.user.PharmacyAdmin;

import java.util.List;

public interface PharmacyAdminService {
    List<PharmacyAdmin> findAll();

    List<PharmacyAdmin> findAllParametrized(Long id, Long pharmacyID);

    PharmacyAdmin getOne(Long id);

    PharmacyAdmin create(PharmacyAdmin pharmacyAdmin);

    PharmacyAdmin update(PharmacyAdmin pharmacyAdmin);

    Boolean delete(Long id);
}
