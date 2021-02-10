package rs.apoteka.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.business.Offer;

public interface OfferRepository extends JpaRepository<Offer, Long> {
}
