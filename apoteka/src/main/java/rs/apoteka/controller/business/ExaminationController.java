package rs.apoteka.controller.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.business.Consultation;
import rs.apoteka.entity.business.Examination;
import rs.apoteka.service.intf.business.ExaminationService;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/examination")
public class ExaminationController {
    @Autowired
    private ExaminationService examinationService;

    @GetMapping(value = "")
    public ResponseEntity<List<Examination>> findAll() throws Exception {
        return new ResponseEntity<>(examinationService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/free")
    public ResponseEntity<List<Examination>> findAllFree() throws Exception {
        return new ResponseEntity<>(examinationService.findAllFree(), HttpStatus.OK);
    }

    @GetMapping(value = "/reserved")
    public ResponseEntity<List<Examination>> findAllReserved() throws Exception {
        return new ResponseEntity<>(examinationService.findAllReserved(), HttpStatus.OK);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<List<Examination>> findAllParametrized(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) LocalDateTime consultationDate,
            @RequestParam(required = false) Long dermatologistID,
            @RequestParam(required = false) Long pharmacyID,
            @RequestParam(required = false) Long patientID,
            @RequestParam(required = false) Integer duration,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Integer durationFrom,
            @RequestParam(required = false) Integer durationTo,
            @RequestParam(required = false) Double priceFrom,
            @RequestParam(required = false) Double priceTo,
            @RequestParam(required = false) Boolean quickReservation
    ) throws Exception {
        return new ResponseEntity<>(examinationService.findAllParametrized(id, consultationDate, dermatologistID, pharmacyID,
                patientID, duration, price, durationFrom, durationTo, priceFrom, priceTo, quickReservation), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Examination> getOne(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(examinationService.getOne(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Examination> create(@RequestBody Examination examination) throws Exception {
        return new ResponseEntity<>(examinationService.create(examination), HttpStatus.OK);
    }

    @PostMapping(value = "/quick-reserve")
    public ResponseEntity<Examination> quickReserve(@RequestBody Examination examination) throws Exception {
        return new ResponseEntity<>(examinationService.quickReserve(examination), HttpStatus.OK);
    }

    @PostMapping(value = "/cancel")
    public ResponseEntity<Examination> cancel(@RequestBody Examination examination) throws Exception {
        return new ResponseEntity<>(examinationService.cancel(examination), HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Examination> update(@RequestBody Examination examination) throws Exception {
        return new ResponseEntity<>(examinationService.update(examination), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(examinationService.delete(id), HttpStatus.OK);
    }
}
