package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Inventory;

import java.time.LocalDateTime;
import java.util.List;

public interface InventoryService {
    List<Inventory> findAll();

    List<Inventory> findAllParametrized(Long id, Long inventoryItemID, Long supplierID);

    Inventory getOne(Long id);

    Inventory create(Inventory inventory);

    Inventory update(Inventory inventory);

    Boolean delete(Long id);
}
