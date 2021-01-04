package rs.apoteka.controller.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.business.Prescription;
import rs.apoteka.service.intf.business.PrescriptionService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/prescription")
public class PrescriptionController {
    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping(value = "")
    public ResponseEntity<List<Prescription>> findAll() throws Exception {
        return new ResponseEntity<>(prescriptionService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Prescription> getOne(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(prescriptionService.getOne(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Prescription> create(@RequestBody Prescription prescription) throws Exception {
        return new ResponseEntity<>(prescriptionService.create(prescription), HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Prescription> update(@RequestBody Prescription prescription) throws Exception {
        return new ResponseEntity<>(prescriptionService.update(prescription), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(prescriptionService.delete(id), HttpStatus.OK);
    }
}
