package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Pharmacy;

import java.util.List;

public interface PharmacyService {
    List<Pharmacy> findAll();

    Pharmacy getOne(Long id);

    Pharmacy create(Pharmacy pharmacy);

    Pharmacy update(Pharmacy pharmacy);

    Boolean delete(Long id);
}
