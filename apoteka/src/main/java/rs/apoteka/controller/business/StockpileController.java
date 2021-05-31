package rs.apoteka.controller.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.business.Stockpile;
import rs.apoteka.service.intf.business.StockpileService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/stockpile")
public class StockpileController {
    @Autowired
    private StockpileService stockpileService;

    @GetMapping(value = "")
    public ResponseEntity<List<Stockpile>> findAll() throws Exception {
        return new ResponseEntity<>(stockpileService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<List<Stockpile>> findAllParametrized(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long medicineID,
            @RequestParam(required = false) Long pharmacyID,
            @RequestParam(required = false) Integer quantity,
            @RequestParam(required = false) Integer quantityFrom,
            @RequestParam(required = false) Integer quantityTo
    ) throws Exception {
        return new ResponseEntity<>(stockpileService.findAllParametrized(id, medicineID, pharmacyID, quantity, quantityFrom, quantityTo), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Stockpile> getOne(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(stockpileService.getOne(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
    @PostMapping(value = "/create")
    public ResponseEntity<Stockpile> create(@RequestBody Stockpile stockpile) throws Exception {
        return new ResponseEntity<>(stockpileService.create(stockpile), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
    @PostMapping(value = "/update")
    public ResponseEntity<Stockpile> update(@RequestBody Stockpile stockpile) throws Exception {
        return new ResponseEntity<>(stockpileService.update(stockpile), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(stockpileService.delete(id), HttpStatus.OK);
    }
}
