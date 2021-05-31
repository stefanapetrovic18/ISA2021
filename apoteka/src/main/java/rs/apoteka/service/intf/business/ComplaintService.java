package rs.apoteka.service.intf.business;

import rs.apoteka.dto.ComplaintResponse;
import rs.apoteka.entity.business.Complaint;
import rs.apoteka.exception.*;

import java.time.LocalDateTime;
import java.util.List;

public interface ComplaintService {
    List<Complaint> findAll();

    List<Complaint> findAllParametrized(Long id, Long patientID, Boolean resolved);

    Complaint getOne(Long id);

    Complaint create(Complaint complaint);

    Complaint submitPharmacist(Complaint complaint, Long pharmacistID) throws InvalidComplaintException, UserNotFoundException;

    Complaint submitDermatologist(Complaint complaint, Long dermatologistID) throws UserNotFoundException, InvalidComplaintException;

    Complaint submitPharmacy(Complaint complaint, Long pharmacyID) throws InvalidComplaintException, UserNotFoundException;

    void answer(ComplaintResponse response) throws ComplaintResolvedException, UserNotFoundException, AuthMismatchException;

    Complaint update(Complaint complaint) throws UserNotFoundException, AuthMismatchException;

    Boolean delete(Long id);
}
