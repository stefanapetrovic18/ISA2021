package rs.apoteka.controller.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.business.Consultation;
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

    @GetMapping(value = "/filter")
    public ResponseEntity<List<Offer>> findAllParametrized(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long orderID,
            @RequestParam(required = false) LocalDateTime shippingDate,
            @RequestParam(required = false) LocalDateTime shippingDateFrom,
            @RequestParam(required = false) LocalDateTime shippingDateUntil
    ) throws Exception {
        return new ResponseEntity<>(offerService.findAllParametrized(id, orderID, shippingDate, shippingDateFrom, shippingDateUntil), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Offer> getOne(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(offerService.getOne(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Offer> create(@RequestBody Offer offer) throws Exception {
        return new ResponseEntity<>(offerService.create(offer), HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Offer> update(@RequestBody Offer offer) throws Exception {
        return new ResponseEntity<>(offerService.update(offer), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(offerService.delete(id), HttpStatus.OK);
    }
}
