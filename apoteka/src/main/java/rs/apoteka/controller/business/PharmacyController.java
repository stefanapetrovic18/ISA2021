package rs.apoteka.controller.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.dto.BusinessReport;
import rs.apoteka.entity.business.Pharmacy;
import rs.apoteka.entity.business.Rating;
import rs.apoteka.service.intf.business.PharmacyService;

import java.time.LocalDate;
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

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping(value = "/subs")
    public ResponseEntity<List<Pharmacy>> findSubs() throws Exception {
        return new ResponseEntity<>(pharmacyService.findSubs(), HttpStatus.OK);
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

    @GetMapping(value = "/medicine")
    public ResponseEntity<List<Pharmacy>> findAllContainingMedicine(@RequestParam Long medicineID) throws Exception {
        return new ResponseEntity<>(pharmacyService.findAllContainingMedicine(medicineID), HttpStatus.OK);
    }

    @GetMapping(value = "/free")
    public ResponseEntity<List<Pharmacy>> findAllByPharmacistFreeAt(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime) throws Exception {
        return new ResponseEntity<>(pharmacyService.findAllByPharmacistFreeAt(localDateTime), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping(value = "/subscribe")
    public ResponseEntity<Boolean> subscribe(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(pharmacyService.subscribe(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping(value = "/check-sub-status")
    public ResponseEntity<Boolean> checkSubStatus(@RequestParam(value = "pharmacyID") Long pharmacyID) throws Exception {
        return new ResponseEntity<>(pharmacyService.checkSubStatus(pharmacyID), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping(value = "/get-rating")
    public ResponseEntity<Rating> getRating(@RequestParam(value = "pharmacyID") Long pharmacyID) throws Exception {
        return new ResponseEntity<>(pharmacyService.getRating(pharmacyID), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping(value = "/unsubscribe")
    public ResponseEntity<Boolean> unsubscribe(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(pharmacyService.unsubscribe(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
    @GetMapping(value = "/business-report")
    public ResponseEntity<BusinessReport> getBusinessReport(
            @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate profitFrom,
            @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate profitUntil,
            @RequestParam(required = true) Integer year
    ) throws Exception {
        return new ResponseEntity<>(pharmacyService.getBusinessReport(profitFrom, profitUntil, year), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
    @PostMapping(value = "/create")
    public ResponseEntity<Pharmacy> create(@RequestBody Pharmacy pharmacy) throws Exception {
        return new ResponseEntity<>(pharmacyService.create(pharmacy), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
    @PostMapping(value = "/update")
    public ResponseEntity<Pharmacy> update(@RequestBody Pharmacy pharmacy) throws Exception {
        return new ResponseEntity<>(pharmacyService.update(pharmacy), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(pharmacyService.delete(id), HttpStatus.OK);
    }
}
