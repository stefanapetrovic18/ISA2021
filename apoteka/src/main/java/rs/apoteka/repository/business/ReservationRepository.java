package rs.apoteka.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.business.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
