package rs.apoteka.controller.business;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.dto.ComplaintResponse;
import rs.apoteka.entity.business.Complaint;
import rs.apoteka.service.intf.business.ComplaintService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/complaint")
public class ComplaintController {
    private static final Gson gson = new Gson();
    @Autowired
    private ComplaintService complaintService;

    @GetMapping(value = "")
    public ResponseEntity<List<Complaint>> findAll() throws Exception {
        return new ResponseEntity<>(complaintService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<List<Complaint>> findAllParametrized(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long patientID,
            @RequestParam(required = false) Boolean resolved
    ) throws Exception {
        return new ResponseEntity<>(complaintService.findAllParametrized(id, patientID, resolved), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Complaint> getOne(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(complaintService.getOne(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
    @PostMapping(value = "/create")
    public ResponseEntity<Complaint> create(@RequestBody Complaint complaint) throws Exception {
        return new ResponseEntity<>(complaintService.create(complaint), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @PostMapping(value = "/submit/pharmacy")
    public ResponseEntity<Complaint> submitPharmacy(@RequestBody Complaint complaint, @RequestParam Long pharmacyID) throws Exception {
        return new ResponseEntity<>(complaintService.submitPharmacy(complaint, pharmacyID), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @PostMapping(value = "/submit/pharmacist")
    public ResponseEntity<Complaint> submitPharmacist(@RequestBody Complaint complaint, @RequestParam Long pharmacistID) throws Exception {
        return new ResponseEntity<>(complaintService.submitPharmacist(complaint, pharmacistID), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @PostMapping(value = "/submit/dermatologist")
    public ResponseEntity<Complaint> submitDermatologist(@RequestBody Complaint complaint, @RequestParam Long dermatologistID) throws Exception {
        return new ResponseEntity<>(complaintService.submitDermatologist(complaint, dermatologistID), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
    @PostMapping(value = "/answer")
    public ResponseEntity<?> answer(@RequestBody ComplaintResponse response) throws Exception {
        complaintService.answer(response);
        return new ResponseEntity<>(gson.toJson("Odgovor na žalbu je poslat."), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
    @PostMapping(value = "/update")
    public ResponseEntity<Complaint> update(@RequestBody Complaint complaint) throws Exception {
        return new ResponseEntity<>(complaintService.update(complaint), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(complaintService.delete(id), HttpStatus.OK);
    }
}
