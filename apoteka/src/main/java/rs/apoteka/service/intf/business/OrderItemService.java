package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> findAll();

    List<OrderItem> findAllParametrized(Long id, Long medicineID, Integer quantity, Integer quantityFrom, Integer quantityTo);

    OrderItem getOne(Long id);

    OrderItem create(OrderItem orderItem);

    OrderItem update(OrderItem orderItem);

    Boolean delete(Long id);
}
