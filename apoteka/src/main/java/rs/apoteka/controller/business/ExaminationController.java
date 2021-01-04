package rs.apoteka.controller.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.business.Examination;
import rs.apoteka.service.intf.business.ExaminationService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/examination")
public class ExaminationController {
    @Autowired
    private ExaminationService examinationService;

    @GetMapping(value = "")
    public ResponseEntity<List<Examination>> findAll() throws Exception {
        return new ResponseEntity<>(examinationService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Examination> getOne(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(examinationService.getOne(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Examination> create(@RequestBody Examination examination) throws Exception {
        return new ResponseEntity<>(examinationService.create(examination), HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Examination> update(@RequestBody Examination examination) throws Exception {
        return new ResponseEntity<>(examinationService.update(examination), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(examinationService.delete(id), HttpStatus.OK);
    }
}
