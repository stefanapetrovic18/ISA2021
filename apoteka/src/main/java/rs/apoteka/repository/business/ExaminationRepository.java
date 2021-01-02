package rs.apoteka.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.business.Examination;

public interface ExaminationRepository extends JpaRepository<Examination, Long> {
}
