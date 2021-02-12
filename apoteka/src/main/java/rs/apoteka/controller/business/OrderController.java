package rs.apoteka.controller.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.business.Order;
import rs.apoteka.service.intf.business.OrderService;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping(value = "")
    public ResponseEntity<List<Order>> findAll() throws Exception {
        return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<List<Order>> findAllParametrized(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long orderItemID,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime expiryDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime expiryDateFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime expiryDateUntil
    ) throws Exception {
        return new ResponseEntity<>(orderService.findAllParametrized(id, orderItemID, expiryDate, expiryDateFrom, expiryDateUntil), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Order> getOne(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(orderService.getOne(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
    @PostMapping(value = "/create")
    public ResponseEntity<Order> create(@RequestBody Order order) throws Exception {
        return new ResponseEntity<>(orderService.create(order), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
    @PostMapping(value = "/update")
    public ResponseEntity<Order> update(@RequestBody Order order) throws Exception {
        return new ResponseEntity<>(orderService.update(order), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(orderService.delete(id), HttpStatus.OK);
    }
}
