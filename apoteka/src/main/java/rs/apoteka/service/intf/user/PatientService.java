package rs.apoteka.service.intf.user;

import rs.apoteka.entity.user.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> findAll();

    Patient findByUsername(String username);

    List<Patient> findAllParametrized(Long id, Long allergyMedicineID, Long consultationID,
                                      Long examinationID, Integer points, Integer pointsFrom, Integer pointsTo);

    Patient getOne(Long id);

    Patient create(Patient patient);

    Patient update(Patient patient);

    Patient updateSubs(Patient patient);

    Boolean delete(Long id);
}
