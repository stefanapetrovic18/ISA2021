package rs.apoteka.controller.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.business.Consultation;
import rs.apoteka.entity.business.Pharmacy;
import rs.apoteka.service.intf.business.PharmacyService;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/pharmacy")
public class PharmacyController {
    @Autowired
    private PharmacyService pharmacyService;

    @GetMapping(value = "")
    public ResponseEntity<List<Pharmacy>> findAll() throws Exception {
        return new ResponseEntity<>(pharmacyService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<List<Pharmacy>> findAllParametrized(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long pharmacistID,
            @RequestParam(required = false) Long dermatologistID,
            @RequestParam(required = false) Long consultationID,
            @RequestParam(required = false) Long adminID,
            @RequestParam(required = false) Long examinationID,
            @RequestParam(required = false) Long promotionID,
            @RequestParam(required = false) Long userID,
            @RequestParam(required = false) Long pricelistID,
            @RequestParam(required = false) Double rating,
            @RequestParam(required = false) Double ratingFrom,
            @RequestParam(required = false) Double ratingTo
    ) throws Exception {
        return new ResponseEntity<>(pharmacyService.findAllParametrized(id, pharmacistID, dermatologistID, consultationID,
                adminID, examinationID, promotionID, userID, pricelistID, rating, ratingFrom, ratingTo), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Pharmacy> getOne(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(pharmacyService.getOne(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Pharmacy> create(@RequestBody Pharmacy pharmacy) throws Exception {
        return new ResponseEntity<>(pharmacyService.create(pharmacy), HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Pharmacy> update(@RequestBody Pharmacy pharmacy) throws Exception {
        return new ResponseEntity<>(pharmacyService.update(pharmacy), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(pharmacyService.delete(id), HttpStatus.OK);
    }
}
