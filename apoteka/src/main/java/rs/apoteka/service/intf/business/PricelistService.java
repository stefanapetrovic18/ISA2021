package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Pricelist;

import java.util.List;

public interface PricelistService {
    List<Pricelist> findAll();

    Pricelist getOne(Long id);

    Pricelist create(Pricelist pricelist);

    Pricelist update(Pricelist pricelist);

    Boolean delete(Long id);
}
