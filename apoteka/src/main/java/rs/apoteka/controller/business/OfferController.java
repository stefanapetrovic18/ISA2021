package rs.apoteka.controller.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.business.Offer;
import rs.apoteka.service.intf.business.OfferService;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/offer")
public class OfferController {
    @Autowired
    private OfferService offerService;

    @GetMapping(value = "")
    public ResponseEntity<List<Offer>> findAll() throws Exception {
        return new ResponseEntity<>(offerService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/non-accepted")
    public ResponseEntity<List<Offer>> findNonAccepted() throws Exception {
        return new ResponseEntity<>(offerService.findNonAccepted(), HttpStatus.OK);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<List<Offer>> findAllParametrized(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long orderID,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime shippingDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime shippingDateFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime shippingDateUntil
    ) throws Exception {
        return new ResponseEntity<>(offerService.findAllParametrized(id, orderID, shippingDate, shippingDateFrom, shippingDateUntil), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Offer> getOne(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(offerService.getOne(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @PostMapping(value = "/create")
    public ResponseEntity<Offer> create(@RequestBody Offer offer) throws Exception {
        return new ResponseEntity<>(offerService.create(offer), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
    @PostMapping(value = "/accept")
    public ResponseEntity<Offer> accept(@RequestBody Offer offer) throws Exception {
        return new ResponseEntity<>(offerService.accept(offer), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @PostMapping(value = "/update")
    public ResponseEntity<Offer> update(@RequestBody Offer offer) throws Exception {
        return new ResponseEntity<>(offerService.update(offer), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(offerService.delete(id), HttpStatus.OK);
    }
}
