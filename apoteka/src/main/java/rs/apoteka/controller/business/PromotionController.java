package rs.apoteka.controller.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.business.Promotion;
import rs.apoteka.service.intf.business.PromotionService;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/promotion")
public class PromotionController {
    @Autowired
    private PromotionService promotionService;

    @GetMapping(value = "")
    public ResponseEntity<List<Promotion>> findAll() throws Exception {
        return new ResponseEntity<>(promotionService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<List<Promotion>> findAllParametrized(
            @RequestParam(required = false) Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false) Long pharmacyID,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description
    ) throws Exception {
        return new ResponseEntity<>(promotionService.findAllParametrized(id, startDate, endDate, pharmacyID, title, description), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Promotion> getOne(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(promotionService.getOne(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Promotion> create(@RequestBody Promotion promotion) throws Exception {
        return new ResponseEntity<>(promotionService.create(promotion), HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Promotion> update(@RequestBody Promotion promotion) throws Exception {
        return new ResponseEntity<>(promotionService.update(promotion), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(promotionService.delete(id), HttpStatus.OK);
    }
}
