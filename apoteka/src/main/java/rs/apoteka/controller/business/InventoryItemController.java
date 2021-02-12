package rs.apoteka.controller.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.business.InventoryItem;
import rs.apoteka.service.intf.business.InventoryItemService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/inventory-item")
public class InventoryItemController {
    @Autowired
    private InventoryItemService inventoryItemService;

    @GetMapping(value = "")
    public ResponseEntity<List<InventoryItem>> findAll() throws Exception {
        return new ResponseEntity<>(inventoryItemService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<List<InventoryItem>> findAllParametrized(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long medicineID,
            @RequestParam(required = false) Integer quantity,
            @RequestParam(required = false) Integer quantityFrom,
            @RequestParam(required = false) Integer quantityTo
    ) throws Exception {
        return new ResponseEntity<>(inventoryItemService.findAllParametrized(id, medicineID,
                quantity, quantityFrom, quantityTo), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<InventoryItem> getOne(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(inventoryItemService.getOne(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @PostMapping(value = "/create")
    public ResponseEntity<InventoryItem> create(@RequestBody InventoryItem inventoryItem) throws Exception {
        return new ResponseEntity<>(inventoryItemService.create(inventoryItem), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @PostMapping(value = "/update")
    public ResponseEntity<InventoryItem> update(@RequestBody InventoryItem inventoryItem) throws Exception {
        return new ResponseEntity<>(inventoryItemService.update(inventoryItem), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(inventoryItemService.delete(id), HttpStatus.OK);
    }
}
