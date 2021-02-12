package rs.apoteka.controller.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.business.OrderItem;
import rs.apoteka.service.intf.business.OrderItemService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/order-item")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @GetMapping(value = "")
    public ResponseEntity<List<OrderItem>> findAll() throws Exception {
        return new ResponseEntity<>(orderItemService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<List<OrderItem>> findAllParametrized(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long medicineID,
            @RequestParam(required = false) Integer quantity,
            @RequestParam(required = false) Integer quantityFrom,
            @RequestParam(required = false) Integer quantityTo
    ) throws Exception {
        return new ResponseEntity<>(orderItemService.findAllParametrized(id, medicineID,
                quantity, quantityFrom, quantityTo), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<OrderItem> getOne(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(orderItemService.getOne(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
    @PostMapping(value = "/create")
    public ResponseEntity<OrderItem> create(@RequestBody OrderItem orderItem) throws Exception {
        return new ResponseEntity<>(orderItemService.create(orderItem), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
    @PostMapping(value = "/update")
    public ResponseEntity<OrderItem> update(@RequestBody OrderItem orderItem) throws Exception {
        return new ResponseEntity<>(orderItemService.update(orderItem), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(orderItemService.delete(id), HttpStatus.OK);
    }
}
