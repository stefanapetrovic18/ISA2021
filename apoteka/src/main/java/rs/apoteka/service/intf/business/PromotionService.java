package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Promotion;

import java.util.List;

public interface PromotionService {
    List<Promotion> findAll();

    Promotion getOne(Long id);

    Promotion create(Promotion promotion);

    Promotion update(Promotion promotion);

    Boolean delete(Long id);
}
