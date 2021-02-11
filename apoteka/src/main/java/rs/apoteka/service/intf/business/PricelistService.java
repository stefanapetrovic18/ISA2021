package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Pricelist;

import java.time.LocalDateTime;
import java.util.List;

public interface PricelistService {
    List<Pricelist> findAll();

    List<Pricelist> findAllParametrized(Long id, Long itemID, LocalDateTime validFrom, LocalDateTime validUntil);

    Pricelist getOne(Long id);

    Pricelist create(Pricelist pricelist) throws Exception;

    Pricelist update(Pricelist pricelist);

    Boolean delete(Long id);
}
