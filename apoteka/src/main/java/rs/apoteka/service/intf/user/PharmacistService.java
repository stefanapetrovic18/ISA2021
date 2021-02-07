package rs.apoteka.service.intf.user;

import rs.apoteka.entity.user.Pharmacist;

import java.util.List;

public interface PharmacistService {
    List<Pharmacist> findAll();

    Pharmacist getOne(Long id);

    Pharmacist create(Pharmacist pharmacist) throws Exception;

    Pharmacist update(Pharmacist pharmacist);

    Boolean delete(Long id);
}
