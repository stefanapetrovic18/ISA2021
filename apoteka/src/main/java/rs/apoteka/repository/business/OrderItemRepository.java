package rs.apoteka.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.business.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
