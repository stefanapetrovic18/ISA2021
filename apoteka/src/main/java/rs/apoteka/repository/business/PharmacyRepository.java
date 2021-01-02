package rs.apoteka.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.business.Pharmacy;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
}
