package rs.apoteka.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.business.Pricelist;

public interface PricelistRepository extends JpaRepository<Pricelist, Long> {
}
