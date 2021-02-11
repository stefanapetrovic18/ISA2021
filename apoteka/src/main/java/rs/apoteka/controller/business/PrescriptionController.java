package rs.apoteka.controller.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.business.Prescription;
import rs.apoteka.service.intf.business.PrescriptionService;

import java.time.LocalDateTime;
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

    @GetMapping(value = "/filter")
    public ResponseEntity<List<Prescription>> findAllParametrized(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String code,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime issueDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime issueDateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime issueDateEnd,
            @RequestParam(required = false) Long pharmacyID,
            @RequestParam(required = false) Long patientID,
            @RequestParam(required = false) Long medicineID
    ) throws Exception {
        return new ResponseEntity<>(prescriptionService.findAllParametrized(id, code, issueDate, issueDateStart,
                issueDateEnd, pharmacyID, patientID, medicineID), HttpStatus.OK);
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
