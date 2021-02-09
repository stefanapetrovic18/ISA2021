package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Promotion;

import java.time.LocalDateTime;
import java.util.List;

public interface PromotionService {
    List<Promotion> findAll();

    List<Promotion> findAllParametrized(Long id, LocalDateTime startDate, LocalDateTime endDate, Long pharmacyID,
                                        String title, String description);

    Promotion getOne(Long id);

    Promotion create(Promotion promotion);

    Promotion update(Promotion promotion);

    Boolean delete(Long id);
}
