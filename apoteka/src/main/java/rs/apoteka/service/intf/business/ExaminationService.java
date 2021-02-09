package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Examination;

import java.time.LocalDateTime;
import java.util.List;

public interface ExaminationService {
    List<Examination> findAll();

    List<Examination> findAllParametrized(Long id, LocalDateTime examinationDate, Long dermatologistID, Long pharmacyID,
                                          Long patientID, Integer duration, Double price, Integer durationFrom,
                                          Integer durationTo, Double priceFrom, Double priceTo, Boolean quickReservation);

    Examination getOne(Long id);

    Examination create(Examination examination);

    Examination update(Examination examination);

    Boolean delete(Long id);
}
