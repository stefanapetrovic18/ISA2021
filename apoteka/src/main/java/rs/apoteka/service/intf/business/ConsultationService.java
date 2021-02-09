package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Consultation;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultationService {
    List<Consultation> findAll();

    List<Consultation> findAllParametrized(Long id, LocalDateTime consultationDate, Long pharmacistID, Long pharmacyID,
                                           Long patientID, Integer duration, Double price, Integer durationFrom,
                                           Integer durationTo, Double priceFrom, Double priceTo);

    Consultation getOne(Long id);

    Consultation create(Consultation consultation);

    Consultation update(Consultation consultation);

    Boolean delete(Long id);
}
