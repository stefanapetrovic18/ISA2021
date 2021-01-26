package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Reservation;

import java.util.List;

public interface ReservationService {
    List<Reservation> findAll();

    Reservation getOne(Long id);

    Reservation create(Reservation reservation);

    Reservation update(Reservation reservation);

    Boolean delete(Long id);
}
