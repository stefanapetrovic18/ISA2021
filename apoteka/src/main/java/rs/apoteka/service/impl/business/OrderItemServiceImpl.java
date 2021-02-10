package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.OrderItem;
import rs.apoteka.repository.business.OrderItemRepository;
import rs.apoteka.service.intf.business.OrderItemService;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    @Override
    public List<OrderItem> findAllParametrized(Long id, Long medicineID,
                                          Integer quantity, Integer quantityFrom, Integer quantityTo) {
        List<OrderItem> orderItems = findAll();
        if (id != null) {
            orderItems.removeIf(i -> !i.getId().equals(id));
        }
        if (medicineID != null) {
            orderItems.removeIf(i -> !i.getMedicine().getId().equals(medicineID));
        }
        if (quantity != null) {
            orderItems.removeIf(i -> i.getQuantity().equals(quantity));
        }
        if (quantityFrom != null) {
            orderItems.removeIf(i -> i.getQuantity() > quantityFrom);
        }
        if (quantityTo != null) {
            orderItems.removeIf(i -> i.getQuantity() > quantityTo);
        }
        return orderItems;
    }

    @Override
    public OrderItem getOne(Long id) {
        return orderItemRepository.getOne(id);
    }

    @Override
    public OrderItem create(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem update(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public Boolean delete(Long id) {
        orderItemRepository.deleteById(id);
        return true;
    }
}
