package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Examination;
import rs.apoteka.exception.AppointmentBookingException;
import rs.apoteka.exception.PatientPenalizedException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ExaminationService {
    List<Examination> findAll();

    List<Examination> findAllFree();

    List<Examination> findAllReserved();

    List<Examination> findAllParametrized(Long id, LocalDateTime examinationDate, Long dermatologistID, Long pharmacyID,
                                          Long patientID, Integer duration, Double price, Integer durationFrom,
                                          Integer durationTo, Double priceFrom, Double priceTo, Boolean quickReservation);

    List<Examination> findAllByPharmacyIDAndDateRange(Long pharmacyID, LocalDate dateFrom, LocalDate dateUntil);

    Examination getOne(Long id);

    Examination create(Examination examination) throws AppointmentBookingException;

    Examination quickReserve(Examination examination) throws AppointmentBookingException, PatientPenalizedException;

    Examination cancel(Examination examination) throws Exception;

    Examination update(Examination examination) throws AppointmentBookingException;

    Boolean delete(Long id);
}
