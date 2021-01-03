package rs.apoteka.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.user.SystemAdmin;
import rs.apoteka.service.intf.user.SystemAdminService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/system-admin")
public class SystemAdminController {
    @Autowired
    private SystemAdminService systemAdminService;

    @GetMapping(value = "")
    public ResponseEntity<List<SystemAdmin>> findAll() throws Exception {
        return new ResponseEntity<>(systemAdminService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<SystemAdmin> getOne(@RequestParam Long id) throws Exception {
        return new ResponseEntity<>(systemAdminService.getOne(id), HttpStatus.OK);
    }

    @GetMapping(value = "/create")
    public ResponseEntity<SystemAdmin> create(@RequestBody SystemAdmin systemAdmin) throws Exception {
        return new ResponseEntity<>(systemAdminService.create(systemAdmin), HttpStatus.OK);
    }

    @GetMapping(value = "/update")
    public ResponseEntity<SystemAdmin> update(@RequestBody SystemAdmin systemAdmin) throws Exception {
        return new ResponseEntity<>(systemAdminService.update(systemAdmin), HttpStatus.OK);
    }

    @GetMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam Long id) throws Exception {
        return new ResponseEntity<>(systemAdminService.delete(id), HttpStatus.OK);
    }
}
