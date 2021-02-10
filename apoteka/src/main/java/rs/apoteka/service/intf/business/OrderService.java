package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    List<Order> findAll();

    List<Order> findAllParametrized(Long id, Long orderItemID, LocalDateTime expiryDate, LocalDateTime expiryDateFrom, LocalDateTime expiryDateUntil);

    Order getOne(Long id);

    Order create(Order order);

    Order update(Order order);

    Boolean delete(Long id);
}
