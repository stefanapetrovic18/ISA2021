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

    @GetMapping(value = "")
    public ResponseEntity<Item> getOne(@RequestParam Long id) throws Exception {
        return new ResponseEntity<>(itemService.getOne(id), HttpStatus.OK);
    }

    @GetMapping(value = "/create")
    public ResponseEntity<Item> create(@RequestBody Item item) throws Exception {
        return new ResponseEntity<>(itemService.create(item), HttpStatus.OK);
    }

    @GetMapping(value = "/update")
    public ResponseEntity<Item> update(@RequestBody Item item) throws Exception {
        return new ResponseEntity<>(itemService.update(item), HttpStatus.OK);
    }

    @GetMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam Long id) throws Exception {
        return new ResponseEntity<>(itemService.delete(id), HttpStatus.OK);
    }
}
