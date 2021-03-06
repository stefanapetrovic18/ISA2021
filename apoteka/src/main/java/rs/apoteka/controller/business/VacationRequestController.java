package rs.apoteka.controller.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.entity.business.VacationRequest;
import rs.apoteka.service.intf.business.VacationRequestService;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/vacation-request")
public class VacationRequestController {
    @Autowired
    private VacationRequestService vacationRequestService;

    @GetMapping(value = "")
    public ResponseEntity<List<VacationRequest>> findAll() throws Exception {
        return new ResponseEntity<>(vacationRequestService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<List<VacationRequest>> findAllParametrized(
            @RequestParam(required = false) Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime vacationStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime vacationEnd,
            @RequestParam(required = false) Long pharmacyID,
            @RequestParam(required = false) Long employeeID,
            @RequestParam(required = false) Boolean accepted
    ) throws Exception {
        return new ResponseEntity<>(vacationRequestService.findAllParametrized(id, vacationStart, vacationEnd,
                pharmacyID, employeeID, accepted), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<VacationRequest> getOne(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(vacationRequestService.getOne(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_DERMATOLOGIST') or hasRole('ROLE_PHARMACIST')")
    @PostMapping(value = "/create")
    public ResponseEntity<VacationRequest> create(@RequestBody VacationRequest vacationRequest) throws Exception {
        return new ResponseEntity<>(vacationRequestService.create(vacationRequest), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
    @PostMapping(value = "/accept")
    public ResponseEntity<VacationRequest> quickReserve(@RequestBody VacationRequest vacationRequest) throws Exception {
        return new ResponseEntity<>(vacationRequestService.accept(vacationRequest), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
    @PostMapping(value = "/reject")
    public ResponseEntity<VacationRequest> cancel(@RequestBody VacationRequest vacationRequest) throws Exception {
        return new ResponseEntity<>(vacationRequestService.reject(vacationRequest), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_DERMATOLOGIST') or hasRole('ROLE_PHARMACIST')")
    @PostMapping(value = "/update")
    public ResponseEntity<VacationRequest> update(@RequestBody VacationRequest vacationRequest) throws Exception {
        return new ResponseEntity<>(vacationRequestService.update(vacationRequest), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_DERMATOLOGIST') or hasRole('ROLE_PHARMACIST') or hasRole('ROLE_PHARMACY_ADMIN')")
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(vacationRequestService.delete(id), HttpStatus.OK);
    }
}
