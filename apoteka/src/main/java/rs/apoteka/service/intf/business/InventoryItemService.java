package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.InventoryItem;

import java.util.List;

public interface InventoryItemService {
    List<InventoryItem> findAll();

    List<InventoryItem> findAllParametrized(Long id, Long medicineID, Integer quantity, Integer quantityFrom, Integer quantityTo);

    InventoryItem getOne(Long id);

    InventoryItem create(InventoryItem inventoryItem);

    InventoryItem update(InventoryItem inventoryItem);

    Boolean delete(Long id);
}
