package rs.apoteka.service.intf.business;

import rs.apoteka.dto.BusinessReport;
import rs.apoteka.entity.business.Pharmacy;
import rs.apoteka.entity.business.Rating;
import rs.apoteka.exception.UserNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PharmacyService {
    List<Pharmacy> findAll();

    Pharmacy getOne(Long id);

    List<Pharmacy> findAllParametrized(Long id, Long pharmacistID, Long dermatologistID, Long consultationID,
                                       Long adminID, Long examinationID, Long promotionID, Long userID,
                                       Long pricelistID, Double rating, Double ratingFrom, Double ratingTo);

    List<Pharmacy> findSubs();

    List<Pharmacy> findAllContainingMedicine(Long medicineID);

    List<Pharmacy> findAllByPharmacistFreeAt(LocalDateTime localDateTime);

    Boolean checkSubStatus(Long pharmacyID) throws Exception;

    Rating getRating(Long pharmacyID) throws Exception;

    Boolean subscribe(Long id) throws Exception;

    Boolean unsubscribe(Long id) throws Exception;

    BusinessReport getBusinessReport(LocalDate profitFrom, LocalDate profitUntil, Integer year) throws UserNotFoundException;

    Pharmacy create(Pharmacy pharmacy);

    Pharmacy update(Pharmacy pharmacy);

    Boolean delete(Long id);
}
