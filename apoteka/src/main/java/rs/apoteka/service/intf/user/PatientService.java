package rs.apoteka.service.intf.user;

import rs.apoteka.entity.user.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> findAll();

    Patient getOne(Long id);

    Patient create(Patient patient);

    Patient update(Patient patient);

    Boolean delete(Long id);
}
