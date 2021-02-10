package rs.apoteka.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.user.Patient;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByUsername(String username);
}
