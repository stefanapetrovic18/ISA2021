package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.InventoryItem;
import rs.apoteka.repository.business.InventoryItemRepository;
import rs.apoteka.service.intf.business.InventoryItemService;

import java.util.List;

@Service
public class InventoryItemServiceImpl implements InventoryItemService {
    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @Override
    public List<InventoryItem> findAll() {
        return inventoryItemRepository.findAll();
    }

    @Override
    public List<InventoryItem> findAllParametrized(Long id, Long medicineID,
                                                   Integer quantity, Integer quantityFrom, Integer quantityTo) {
        List<InventoryItem> inventoryItems = findAll();
        if (id != null) {
            inventoryItems.removeIf(i -> !i.getId().equals(id));
        }
        if (medicineID != null) {
            inventoryItems.removeIf(i -> !i.getMedicine().getId().equals(medicineID));
        }
        if (quantity != null) {
            inventoryItems.removeIf(i -> i.getQuantity().equals(quantity));
        }
        if (quantityFrom != null) {
            inventoryItems.removeIf(i -> i.getQuantity() > quantityFrom);
        }
        if (quantityTo != null) {
            inventoryItems.removeIf(i -> i.getQuantity() > quantityTo);
        }
        return inventoryItems;
    }

    @Override
    public InventoryItem getOne(Long id) {
        return inventoryItemRepository.getOne(id);
    }

    @Override
    public InventoryItem create(InventoryItem inventoryItem) {
        return inventoryItemRepository.save(inventoryItem);
    }

    @Override
    public InventoryItem update(InventoryItem inventoryItem) {
        return inventoryItemRepository.save(inventoryItem);
    }

    @Override
    public Boolean delete(Long id) {
        inventoryItemRepository.deleteById(id);
        return true;
    }
}
