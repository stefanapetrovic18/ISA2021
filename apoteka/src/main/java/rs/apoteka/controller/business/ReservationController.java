package rs.apoteka.controller.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.business.Reservation;
import rs.apoteka.service.intf.business.ReservationService;

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

    @GetMapping(value = "/search")
    public ResponseEntity<Reservation> getOne(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(reservationService.getOne(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) throws Exception {
        return new ResponseEntity<>(reservationService.create(reservation), HttpStatus.OK);
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
