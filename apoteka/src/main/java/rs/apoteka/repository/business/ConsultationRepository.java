package rs.apoteka.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.business.Consultation;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
}
