package rs.apoteka.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.business.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
