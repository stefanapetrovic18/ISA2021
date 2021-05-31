package rs.apoteka.controller.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.business.Rating;
import rs.apoteka.service.intf.business.RatingService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/rating")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @GetMapping(value = "")
    public ResponseEntity<List<Rating>> findAll() throws Exception {
        return new ResponseEntity<>(ratingService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<List<Rating>> findAllParametrized(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long patientID,
            @RequestParam(required = false) Long medicineID,
            @RequestParam(required = false) Long dermatologistID,
            @RequestParam(required = false) Long pharmacistID,
            @RequestParam(required = false) Long pharmacyID
    ) throws Exception {
        return new ResponseEntity<>(ratingService.findAllParametrized(id, patientID, medicineID,
                dermatologistID, pharmacistID, pharmacyID), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Rating> getOne(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(ratingService.getOne(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
    @PostMapping(value = "/create")
    public ResponseEntity<Rating> create(@RequestBody Rating rating) throws Exception {
        return new ResponseEntity<>(ratingService.create(rating), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @PostMapping(value = "/rate/pharmacy")
    public ResponseEntity<Rating> ratePharmacy(@RequestBody Rating rating, @RequestParam Long pharmacyID) throws Exception {
        return new ResponseEntity<>(ratingService.ratePharmacy(rating, pharmacyID), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @PostMapping(value = "/rate/medicine")
    public ResponseEntity<Rating> rateMedicine(@RequestBody Rating rating, @RequestParam Long medicineID) throws Exception {
        return new ResponseEntity<>(ratingService.rateMedicine(rating, medicineID), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @PostMapping(value = "/rate/pharmacist")
    public ResponseEntity<Rating> ratePharmacist(@RequestBody Rating rating, @RequestParam Long pharmacistID) throws Exception {
        return new ResponseEntity<>(ratingService.ratePharmacist(rating, pharmacistID), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @PostMapping(value = "/rate/dermatologist")
    public ResponseEntity<Rating> rateDermatologist(@RequestBody Rating rating, @RequestParam Long dermatologistID) throws Exception {
        return new ResponseEntity<>(ratingService.rateDermatologist(rating, dermatologistID), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
    @PostMapping(value = "/update")
    public ResponseEntity<Rating> update(@RequestBody Rating rating) throws Exception {
        return new ResponseEntity<>(ratingService.update(rating), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(ratingService.delete(id), HttpStatus.OK);
    }
}
