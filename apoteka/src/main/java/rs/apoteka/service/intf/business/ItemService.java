package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Item;

import java.util.List;

public interface ItemService {
    List<Item> findAll();

    List<Item> findAllParametrized(Long id, Long medicineID, Double price, Double priceFrom, Double priceTo,
                                   Integer quantity, Integer quantityFrom, Integer quantityTo, Long pricelistID);

    Item getOne(Long id);

    Item create(Item item);

    Item update(Item item);

    Boolean delete(Long id);
}
