package rs.apoteka.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.business.InventoryItem;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
}
