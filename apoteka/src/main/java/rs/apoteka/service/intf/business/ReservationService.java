package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Reservation;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {
    List<Reservation> findAll();

    List<Reservation> findAllParametrized(Long id, LocalDateTime reservationDate, LocalDateTime reservationDateStart,
                                          LocalDateTime reservationDateEnd, Long pharmacyID, Long medicineID,
                                          Long patientID, Boolean collected, String reservationNumber);

    Reservation getOne(Long id);

    Reservation create(Reservation reservation);

    Reservation update(Reservation reservation);

    Boolean delete(Long id);
}
