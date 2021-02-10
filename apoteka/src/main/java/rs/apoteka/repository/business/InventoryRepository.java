package rs.apoteka.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.business.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
