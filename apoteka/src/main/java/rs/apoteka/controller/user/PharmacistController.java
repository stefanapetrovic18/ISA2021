package rs.apoteka.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.user.Pharmacist;
import rs.apoteka.service.intf.user.PharmacistService;

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

    @GetMapping(value = "")
    public ResponseEntity<Pharmacist> getOne(@RequestParam Long id) throws Exception {
        return new ResponseEntity<>(pharmacistService.getOne(id), HttpStatus.OK);
    }

    @GetMapping(value = "/create")
    public ResponseEntity<Pharmacist> create(@RequestBody Pharmacist pharmacist) throws Exception {
        return new ResponseEntity<>(pharmacistService.create(pharmacist), HttpStatus.OK);
    }

    @GetMapping(value = "/update")
    public ResponseEntity<Pharmacist> update(@RequestBody Pharmacist pharmacist) throws Exception {
        return new ResponseEntity<>(pharmacistService.update(pharmacist), HttpStatus.OK);
    }

    @GetMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam Long id) throws Exception {
        return new ResponseEntity<>(pharmacistService.delete(id), HttpStatus.OK);
    }
}
