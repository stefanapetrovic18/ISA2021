package rs.apoteka.controller.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.business.Medicine;
import rs.apoteka.service.intf.business.MedicineService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/medicine")
public class MedicineController {
    @Autowired
    private MedicineService medicineService;

    @GetMapping(value = "")
    public ResponseEntity<List<Medicine>> findAll() throws Exception {
        return new ResponseEntity<>(medicineService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<Medicine> getOne(@RequestParam Long id) throws Exception {
        return new ResponseEntity<>(medicineService.getOne(id), HttpStatus.OK);
    }

    @GetMapping(value = "/create")
    public ResponseEntity<Medicine> create(@RequestBody Medicine medicine) throws Exception {
        return new ResponseEntity<>(medicineService.create(medicine), HttpStatus.OK);
    }

    @GetMapping(value = "/update")
    public ResponseEntity<Medicine> update(@RequestBody Medicine medicine) throws Exception {
        return new ResponseEntity<>(medicineService.update(medicine), HttpStatus.OK);
    }

    @GetMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam Long id) throws Exception {
        return new ResponseEntity<>(medicineService.delete(id), HttpStatus.OK);
    }
}
