package rs.apoteka.controller.business;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.business.Reservation;
import rs.apoteka.service.intf.business.ReservationService;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping(value = "")
    public ResponseEntity<List<Reservation>> findAll() throws Exception {
        return new ResponseEntity<>(reservationService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<List<Reservation>> findAllParametrized(
            @RequestParam(required = false) Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime reservationDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime reservationDateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime reservationDateEnd,
            @RequestParam(required = false) Long pharmacyID,
            @RequestParam(required = false) Long medicineID,
            @RequestParam(required = false) Long patientID,
            @RequestParam(required = false) Boolean collected,
            @RequestParam(required = false) String reservationNumber
    ) throws Exception {
        return new ResponseEntity<>(reservationService.findAllParametrized(id, reservationDate, reservationDateStart, reservationDateEnd,
                pharmacyID, medicineID, patientID, collected, reservationNumber), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Reservation> getOne(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(reservationService.getOne(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) throws Exception {
        return new ResponseEntity<>(reservationService.create(reservation), HttpStatus.OK);
    }

    @PostMapping(value = "/reserve")
    public ResponseEntity<Reservation> reserve(@RequestBody Reservation reservation) throws Exception {
        return new ResponseEntity<>(reservationService.reserve(reservation), HttpStatus.OK);
    }

    @PostMapping(value = "/cancel")
    public ResponseEntity<Reservation> cancel(@RequestBody Reservation reservation) throws Exception {
        return new ResponseEntity<>(reservationService.cancel(reservation), HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Reservation> update(@RequestBody Reservation reservation) throws Exception {
        return new ResponseEntity<>(reservationService.update(reservation), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(reservationService.delete(id), HttpStatus.OK);
    }
}
