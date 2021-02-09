package rs.apoteka.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.business.Consultation;
import rs.apoteka.entity.user.Dermatologist;
import rs.apoteka.service.intf.user.DermatologistService;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/dermatologist")
public class DermatologistController {
    @Autowired
    private DermatologistService dermatologistService;

    @GetMapping(value = "")
    public ResponseEntity<List<Dermatologist>> findAll() throws Exception {
        return new ResponseEntity<>(dermatologistService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<List<Dermatologist>> findAllParametrized(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long workingHoursID,
            @RequestParam(required = false) Long pharmacyID,
            @RequestParam(required = false) LocalDateTime vacationStart,
            @RequestParam(required = false) LocalDateTime vacationEnd,
            @RequestParam(required = false) Long examinationID,
            @RequestParam(required = false) Double rating,
            @RequestParam(required = false) Double ratingFrom,
            @RequestParam(required = false) Double ratingTo
    ) throws Exception {
        return new ResponseEntity<>(dermatologistService.findAllParametrized(id, workingHoursID, pharmacyID, vacationStart,
                vacationEnd, examinationID, rating, ratingFrom, ratingTo), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Dermatologist> getOne(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(dermatologistService.getOne(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Dermatologist> create(@RequestBody Dermatologist dermatologist) throws Exception {
        return new ResponseEntity<>(dermatologistService.create(dermatologist), HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Dermatologist> update(@RequestBody Dermatologist dermatologist) throws Exception {
        return new ResponseEntity<>(dermatologistService.update(dermatologist), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(dermatologistService.delete(id), HttpStatus.OK);
    }
}
