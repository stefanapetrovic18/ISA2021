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

    @GetMapping(value = "")
    public ResponseEntity<Prescription> getOne(@RequestParam Long id) throws Exception {
        return new ResponseEntity<>(prescriptionService.getOne(id), HttpStatus.OK);
    }

    @GetMapping(value = "/create")
    public ResponseEntity<Prescription> create(@RequestBody Prescription prescription) throws Exception {
        return new ResponseEntity<>(prescriptionService.create(prescription), HttpStatus.OK);
    }

    @GetMapping(value = "/update")
    public ResponseEntity<Prescription> update(@RequestBody Prescription prescription) throws Exception {
        return new ResponseEntity<>(prescriptionService.update(prescription), HttpStatus.OK);
    }

    @GetMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam Long id) throws Exception {
        return new ResponseEntity<>(prescriptionService.delete(id), HttpStatus.OK);
    }
}
