package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Item;

import java.time.LocalDate;
import java.util.List;

public interface ItemService {
    List<Item> findAll() throws Exception;

    List<Item> findAllParametrized(Long id, Long medicineID, Double price, Double priceFrom, Double priceTo,
                                   Integer quantity, Integer quantityFrom, Integer quantityTo, Long pricelistID);

    Double getPriceAtDate(List<Item> items, Long medicineID, LocalDate priceFrom, LocalDate priceUntil);

    Item getOne(Long id);

    Item create(Item item);

    Item update(Item item);

    Boolean delete(Long id);
}
