package rs.apoteka.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.user.PharmacyAdmin;
import rs.apoteka.service.intf.user.PharmacyAdminService;

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

    @GetMapping(value = "")
    public ResponseEntity<PharmacyAdmin> getOne(@RequestParam Long id) throws Exception {
        return new ResponseEntity<>(pharmacyAdminService.getOne(id), HttpStatus.OK);
    }

    @GetMapping(value = "/create")
    public ResponseEntity<PharmacyAdmin> create(@RequestBody PharmacyAdmin pharmacyAdmin) throws Exception {
        return new ResponseEntity<>(pharmacyAdminService.create(pharmacyAdmin), HttpStatus.OK);
    }

    @GetMapping(value = "/update")
    public ResponseEntity<PharmacyAdmin> update(@RequestBody PharmacyAdmin pharmacyAdmin) throws Exception {
        return new ResponseEntity<>(pharmacyAdminService.update(pharmacyAdmin), HttpStatus.OK);
    }

    @GetMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam Long id) throws Exception {
        return new ResponseEntity<>(pharmacyAdminService.delete(id), HttpStatus.OK);
    }
}
