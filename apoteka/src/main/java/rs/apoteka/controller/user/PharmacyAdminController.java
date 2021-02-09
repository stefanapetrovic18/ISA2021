package rs.apoteka.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.user.Pharmacist;
import rs.apoteka.entity.user.PharmacyAdmin;
import rs.apoteka.service.intf.user.PharmacyAdminService;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/pharmacy-admin")
public class PharmacyAdminController {
    @Autowired
    private PharmacyAdminService pharmacyAdminService;

    @GetMapping(value = "")
    public ResponseEntity<List<PharmacyAdmin>> findAll() throws Exception {
        return new ResponseEntity<>(pharmacyAdminService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<List<PharmacyAdmin>> findAllParametrized(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long pharmacyID
    ) throws Exception {
        return new ResponseEntity<>(pharmacyAdminService.findAllParametrized(id, pharmacyID), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<PharmacyAdmin> getOne(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(pharmacyAdminService.getOne(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<PharmacyAdmin> create(@RequestBody PharmacyAdmin pharmacyAdmin) throws Exception {
        return new ResponseEntity<>(pharmacyAdminService.create(pharmacyAdmin), HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<PharmacyAdmin> update(@RequestBody PharmacyAdmin pharmacyAdmin) throws Exception {
        return new ResponseEntity<>(pharmacyAdminService.update(pharmacyAdmin), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(pharmacyAdminService.delete(id), HttpStatus.OK);
    }
}
