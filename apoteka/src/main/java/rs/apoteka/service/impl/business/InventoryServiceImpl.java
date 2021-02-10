package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Inventory;
import rs.apoteka.repository.business.InventoryRepository;
import rs.apoteka.service.intf.business.InventoryService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public List<Inventory> findAll() {
        return inventoryRepository.findAll();
    }

    @Override
    public List<Inventory> findAllParametrized(Long id, Long inventoryItemID, Long supplierID) {
        List<Inventory> inventory = findAll();
        if (id != null) {
            inventory.removeIf(p -> !p.getId().equals(id));
        }
        if (inventoryItemID != null) {
            inventory = inventory.stream().filter(p -> p.getItems().removeIf(m -> !m.getId().equals(inventoryItemID))).collect(Collectors.toList());
        }
        if (supplierID != null) {
            inventory.removeIf(p -> !p.getSupplier().getId().equals(supplierID));
        }
        return inventory;
    }

    @Override
    public Inventory getOne(Long id) {
        return inventoryRepository.getOne(id);
    }

    @Override
    public Inventory create(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory update(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Override
    public Boolean delete(Long id) {
        inventoryRepository.deleteById(id);
        return true;
    }
}
