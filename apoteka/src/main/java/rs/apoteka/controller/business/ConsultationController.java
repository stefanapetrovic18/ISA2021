package rs.apoteka.controller.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.business.Consultation;
import rs.apoteka.service.intf.business.ConsultationService;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/consultation")
public class ConsultationController {
    @Autowired
    private ConsultationService consultationService;

    @GetMapping(value = "")
    public ResponseEntity<List<Consultation>> findAll() throws Exception {
        return new ResponseEntity<>(consultationService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<List<Consultation>> findAllParametrized(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime consultationDate,
            @RequestParam(required = false) Long pharmacistID,
            @RequestParam(required = false) Long pharmacyID,
            @RequestParam(required = false) Long patientID,
            @RequestParam(required = false) Integer duration,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Integer durationFrom,
            @RequestParam(required = false) Integer durationTo,
            @RequestParam(required = false) Double priceFrom,
            @RequestParam(required = false) Double priceTo
    ) throws Exception {
        return new ResponseEntity<>(consultationService.findAllParametrized(id, consultationDate, pharmacistID, pharmacyID,
                patientID, duration, price, durationFrom, durationTo, priceFrom, priceTo), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Consultation> getOne(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(consultationService.getOne(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Consultation> create(@RequestBody Consultation consultation) throws Exception {
        return new ResponseEntity<>(consultationService.create(consultation), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @PostMapping(value = "/reserve")
    public ResponseEntity<Consultation> reserve(@RequestBody Consultation consultation) throws Exception {
        return new ResponseEntity<>(consultationService.reserve(consultation), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @PostMapping(value = "/cancel")
    public ResponseEntity<Consultation> cancel(@RequestBody Consultation consultation) throws Exception {
        return new ResponseEntity<>(consultationService.cancel(consultation), HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Consultation> update(@RequestBody Consultation consultation) throws Exception {
        return new ResponseEntity<>(consultationService.update(consultation), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(consultationService.delete(id), HttpStatus.OK);
    }
}
