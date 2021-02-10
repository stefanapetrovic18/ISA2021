package rs.apoteka.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.business.VacationRequest;

public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long> {
}
