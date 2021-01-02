package rs.apoteka.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.business.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
}
