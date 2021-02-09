package rs.apoteka.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.user.Dermatologist;
import rs.apoteka.entity.user.Pharmacist;
import rs.apoteka.service.intf.user.PharmacistService;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/pharmacist")
public class PharmacistController {
    @Autowired
    private PharmacistService pharmacistService;

    @GetMapping(value = "")
    public ResponseEntity<List<Pharmacist>> findAll() throws Exception {
        return new ResponseEntity<>(pharmacistService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<List<Pharmacist>> findAllParametrized(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long workingHoursID,
            @RequestParam(required = false) Long pharmacyID,
            @RequestParam(required = false) LocalDateTime vacationStart,
            @RequestParam(required = false) LocalDateTime vacationEnd,
            @RequestParam(required = false) Long consultationID,
            @RequestParam(required = false) Double rating,
            @RequestParam(required = false) Double ratingFrom,
            @RequestParam(required = false) Double ratingTo
    ) throws Exception {
        return new ResponseEntity<>(pharmacistService.findAllParametrized(id, workingHoursID, pharmacyID, vacationStart,
                vacationEnd, consultationID, rating, ratingFrom, ratingTo), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Pharmacist> getOne(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(pharmacistService.getOne(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Pharmacist> create(@RequestBody Pharmacist pharmacist) throws Exception {
        return new ResponseEntity<>(pharmacistService.create(pharmacist), HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Pharmacist> update(@RequestBody Pharmacist pharmacist) throws Exception {
        return new ResponseEntity<>(pharmacistService.update(pharmacist), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(pharmacistService.delete(id), HttpStatus.OK);
    }
}
