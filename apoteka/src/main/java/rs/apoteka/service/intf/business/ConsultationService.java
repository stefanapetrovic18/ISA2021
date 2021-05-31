package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Consultation;
import rs.apoteka.exception.AppointmentBookingException;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultationService {
    List<Consultation> findAll();

    List<Consultation> findAllParametrized(Long id, LocalDateTime consultationDate, Long pharmacistID, Long pharmacyID,
                                           Long patientID, Integer duration, Double price, Integer durationFrom,
                                           Integer durationTo, Double priceFrom, Double priceTo);

    Consultation getOne(Long id);

    Consultation create(Consultation consultation) throws AppointmentBookingException;

    Consultation reserve(Consultation consultation) throws AppointmentBookingException;

    Consultation cancel(Consultation consultation) throws Exception;

    Consultation update(Consultation consultation) throws AppointmentBookingException;

    Boolean delete(Long id);
}
