package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Prescription;

import java.util.List;

public interface PrescriptionService {
    List<Prescription> findAll();

    Prescription getOne(Long id);

    Prescription create(Prescription prescription);

    Prescription update(Prescription prescription);

    Boolean delete(Long id);
}
