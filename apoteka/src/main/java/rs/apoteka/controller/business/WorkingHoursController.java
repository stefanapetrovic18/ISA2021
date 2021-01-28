package rs.apoteka.controller.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.business.WorkingHours;
import rs.apoteka.service.intf.business.WorkingHoursService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/working-hours")
public class WorkingHoursController {
    @Autowired
    private WorkingHoursService workingHoursService;

    @GetMapping(value = "")
    public ResponseEntity<List<WorkingHours>> findAll() throws Exception {
        return new ResponseEntity<>(workingHoursService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<WorkingHours> getOne(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(workingHoursService.getOne(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<WorkingHours> create(@RequestBody WorkingHours workingHours) throws Exception {
        return new ResponseEntity<>(workingHoursService.create(workingHours), HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<WorkingHours> update(@RequestBody WorkingHours workingHours) throws Exception {
        return new ResponseEntity<>(workingHoursService.update(workingHours), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(workingHoursService.delete(id), HttpStatus.OK);
    }
}