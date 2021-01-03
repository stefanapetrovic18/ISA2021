package rs.apoteka.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.user.Supplier;
import rs.apoteka.service.intf.user.SupplierService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @GetMapping(value = "")
    public ResponseEntity<List<Supplier>> findAll() throws Exception {
        return new ResponseEntity<>(supplierService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<Supplier> getOne(@RequestParam Long id) throws Exception {
        return new ResponseEntity<>(supplierService.getOne(id), HttpStatus.OK);
    }

    @GetMapping(value = "/create")
    public ResponseEntity<Supplier> create(@RequestBody Supplier supplier) throws Exception {
        return new ResponseEntity<>(supplierService.create(supplier), HttpStatus.OK);
    }

    @GetMapping(value = "/update")
    public ResponseEntity<Supplier> update(@RequestBody Supplier supplier) throws Exception {
        return new ResponseEntity<>(supplierService.update(supplier), HttpStatus.OK);
    }

    @GetMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam Long id) throws Exception {
        return new ResponseEntity<>(supplierService.delete(id), HttpStatus.OK);
    }
}
