package rs.apoteka.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.business.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
}
