package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Prescription;

import java.time.LocalDateTime;
import java.util.List;

public interface PrescriptionService {
    List<Prescription> findAll();

    List<Prescription> findAllParametrized(Long id, String code, LocalDateTime issueDate, LocalDateTime issueDateStart,
                                           LocalDateTime issueDateEnd, Long pharmacyID, Long patientID, Long medicineID);

    Prescription getOne(Long id);

    Prescription create(Prescription prescription);

    Prescription update(Prescription prescription);

    Boolean delete(Long id);
}
