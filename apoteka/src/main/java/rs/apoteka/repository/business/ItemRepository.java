package rs.apoteka.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.business.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
