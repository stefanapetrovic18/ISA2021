package rs.apoteka.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.user.Patient;
import rs.apoteka.service.intf.user.PatientService;

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

    @GetMapping(value = "")
    public ResponseEntity<Patient> getOne(@RequestParam Long id) throws Exception {
        return new ResponseEntity<>(patientService.getOne(id), HttpStatus.OK);
    }

    @GetMapping(value = "/create")
    public ResponseEntity<Patient> create(@RequestBody Patient patient) throws Exception {
        return new ResponseEntity<>(patientService.create(patient), HttpStatus.OK);
    }

    @GetMapping(value = "/update")
    public ResponseEntity<Patient> update(@RequestBody Patient patient) throws Exception {
        return new ResponseEntity<>(patientService.update(patient), HttpStatus.OK);
    }

    @GetMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam Long id) throws Exception {
        return new ResponseEntity<>(patientService.delete(id), HttpStatus.OK);
    }
}
