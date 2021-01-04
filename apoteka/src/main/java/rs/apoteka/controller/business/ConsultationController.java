package rs.apoteka.controller.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.business.Consultation;
import rs.apoteka.service.intf.business.ConsultationService;

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

    @GetMapping(value = "/search")
    public ResponseEntity<Consultation> getOne(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(consultationService.getOne(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Consultation> create(@RequestBody Consultation consultation) throws Exception {
        return new ResponseEntity<>(consultationService.create(consultation), HttpStatus.OK);
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
