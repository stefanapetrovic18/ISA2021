package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Consultation;
import rs.apoteka.exception.AppointmentBookedException;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultationService {
    List<Consultation> findAll();

    List<Consultation> findAllParametrized(Long id, LocalDateTime consultationDate, Long pharmacistID, Long pharmacyID,
                                           Long patientID, Integer duration, Double price, Integer durationFrom,
                                           Integer durationTo, Double priceFrom, Double priceTo);

    Consultation getOne(Long id);

    Consultation create(Consultation consultation) throws AppointmentBookedException;

    Consultation reserve(Consultation consultation) throws AppointmentBookedException;

    Consultation cancel(Consultation consultation) throws Exception;

    Consultation update(Consultation consultation) throws AppointmentBookedException;

    Boolean delete(Long id);
}
