package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Item;

import java.util.List;

public interface ItemService {
    List<Item> findAll();

    Item getOne(Long id);

    Item create(Item item);

    Item update(Item item);

    Boolean delete(Long id);
}
