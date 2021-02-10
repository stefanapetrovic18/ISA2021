package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Order;
import rs.apoteka.repository.business.OrderRepository;
import rs.apoteka.service.intf.business.OrderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findAllParametrized(Long id, Long orderItemID, LocalDateTime expiryDate, LocalDateTime expiryDateFrom, LocalDateTime expiryDateUntil) {
        List<Order> order = findAll();
        if (id != null) {
            order.removeIf(p -> !p.getId().equals(id));
        }
        if (orderItemID != null) {
            order = order.stream().filter(p -> p.getItems().removeIf(m -> !m.getId().equals(orderItemID))).collect(Collectors.toList());
        }
        if (expiryDate != null) {
            order.removeIf(p -> !p.getExpiryDate().equals(expiryDate));
        }
        if (expiryDateFrom != null) {
            order.removeIf(p -> p.getExpiryDate().isBefore(expiryDateFrom));
        }
        if (expiryDateUntil != null) {
            order.removeIf(p -> p.getExpiryDate().isAfter(expiryDateUntil));
        }
        return order;
    }

    @Override
    public Order getOne(Long id) {
        return orderRepository.getOne(id);
    }

    @Override
    public Order create(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Boolean delete(Long id) {
        orderRepository.deleteById(id);
        return true;
    }
}
