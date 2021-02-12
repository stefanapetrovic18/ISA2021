package rs.apoteka.controller.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.business.Inventory;
import rs.apoteka.service.intf.business.InventoryService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping(value = "")
    public ResponseEntity<List<Inventory>> findAll() throws Exception {
        return new ResponseEntity<>(inventoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<List<Inventory>> findAllParametrized(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long inventoryItemID,
            @RequestParam(required = false) Long supplierID
    ) throws Exception {
        return new ResponseEntity<>(inventoryService.findAllParametrized(id, inventoryItemID, supplierID), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Inventory> getOne(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(inventoryService.getOne(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @PostMapping(value = "/create")
    public ResponseEntity<Inventory> create(@RequestBody Inventory inventory) throws Exception {
        return new ResponseEntity<>(inventoryService.create(inventory), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @PostMapping(value = "/update")
    public ResponseEntity<Inventory> update(@RequestBody Inventory inventory) throws Exception {
        return new ResponseEntity<>(inventoryService.update(inventory), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(inventoryService.delete(id), HttpStatus.OK);
    }
}
