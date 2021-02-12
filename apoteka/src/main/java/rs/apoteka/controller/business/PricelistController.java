package rs.apoteka.controller.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.business.Pricelist;
import rs.apoteka.service.intf.business.PricelistService;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/pricelist")
public class PricelistController {
    @Autowired
    private PricelistService pricelistService;

    @GetMapping(value = "")
    public ResponseEntity<List<Pricelist>> findAll() throws Exception {
        return new ResponseEntity<>(pricelistService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<List<Pricelist>> findAllParametrized(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long itemID,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime validFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime validUntil
    ) throws Exception {
        return new ResponseEntity<>(pricelistService.findAllParametrized(id, itemID, validFrom, validUntil), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Pricelist> getOne(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(pricelistService.getOne(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
    @PostMapping(value = "/create")
    public ResponseEntity<Pricelist> create(@RequestBody Pricelist pricelist) throws Exception {
        return new ResponseEntity<>(pricelistService.create(pricelist), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
    @PostMapping(value = "/update")
    public ResponseEntity<Pricelist> update(@RequestBody Pricelist pricelist) throws Exception {
        return new ResponseEntity<>(pricelistService.update(pricelist), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(pricelistService.delete(id), HttpStatus.OK);
    }
}
