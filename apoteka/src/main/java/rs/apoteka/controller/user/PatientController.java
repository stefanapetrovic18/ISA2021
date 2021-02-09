package rs.apoteka.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.user.Dermatologist;
import rs.apoteka.entity.user.Patient;
import rs.apoteka.service.intf.user.PatientService;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping(value = "")
    public ResponseEntity<List<Patient>> findAll() throws Exception {
        return new ResponseEntity<>(patientService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<List<Patient>> findAllParametrized(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long allergyMedicineID,
            @RequestParam(required = false) Long consultationID,
            @RequestParam(required = false) Long examinationID,
            @RequestParam(required = false) Integer points,
            @RequestParam(required = false) Integer pointsFrom,
            @RequestParam(required = false) Integer pointsTo
    ) throws Exception {
        return new ResponseEntity<>(patientService.findAllParametrized(id, allergyMedicineID, consultationID,
                examinationID, points, pointsFrom, pointsTo), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Patient> getOne(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(patientService.getOne(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Patient> create(@RequestBody Patient patient) throws Exception {
        return new ResponseEntity<>(patientService.create(patient), HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Patient> update(@RequestBody Patient patient) throws Exception {
        return new ResponseEntity<>(patientService.update(patient), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(patientService.delete(id), HttpStatus.OK);
    }
}
