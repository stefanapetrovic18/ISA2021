package rs.apoteka.controller.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.business.Item;
import rs.apoteka.service.intf.business.ItemService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping(value = "")
    public ResponseEntity<List<Item>> findAll() throws Exception {
        return new ResponseEntity<>(itemService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Item> getOne(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(itemService.getOne(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Item> create(@RequestBody Item item) throws Exception {
        return new ResponseEntity<>(itemService.create(item), HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Item> update(@RequestBody Item item) throws Exception {
        return new ResponseEntity<>(itemService.update(item), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(itemService.delete(id), HttpStatus.OK);
    }
}