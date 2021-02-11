package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Pharmacy;

import java.time.LocalDateTime;
import java.util.List;

public interface PharmacyService {
    List<Pharmacy> findAll();

    Pharmacy getOne(Long id);

    List<Pharmacy> findAllParametrized(Long id, Long pharmacistID, Long dermatologistID, Long consultationID,
                                       Long adminID, Long examinationID, Long promotionID, Long userID,
                                       Long pricelistID, Double rating, Double ratingFrom, Double ratingTo);

    List<Pharmacy> findAllByPharmacistFreeAt(LocalDateTime localDateTime);

    Pharmacy create(Pharmacy pharmacy);

    Pharmacy update(Pharmacy pharmacy);

    Boolean delete(Long id);
}
