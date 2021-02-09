package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Promotion;
import rs.apoteka.entity.business.Reservation;
import rs.apoteka.repository.business.ReservationRepository;
import rs.apoteka.service.intf.business.ReservationService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public List<Reservation> findAllParametrized(Long id, LocalDateTime reservationDate, LocalDateTime reservationDateStart,
                                                 LocalDateTime reservationDateEnd, Long pharmacyID, Long medicineID,
                                                 Long patientID, Boolean collected, String reservationNumber) {
        List<Reservation> reservations = findAll();
        if (id != null) {
            reservations.removeIf(p -> !p.getId().equals(id));
        }
        if (collected != null) {
            reservations.removeIf(p -> p.getCollected() != collected);
        }
        if (reservationDate != null) {
            reservations.removeIf(p -> !p.getReservationDate().equals(reservationDate));
        }
        if (pharmacyID != null) {
            reservations.removeIf(p -> !p.getPharmacy().getId().equals(pharmacyID));
        }
        if (medicineID != null) {
            reservations.removeIf(p -> !p.getMedicine().getId().equals(medicineID));
        }
        if (patientID != null) {
            reservations.removeIf(p -> !p.getPatient().getId().equals(patientID));
        }
        if (reservationNumber != null) {
            reservations.removeIf(p -> !p.getReservationNumber().contains(reservationNumber));
        }
        if (reservationDateStart != null) {
            reservations.removeIf(p -> p.getReservationDate().isBefore(reservationDateStart));
        }
        if (reservationDateEnd != null) {
            reservations.removeIf(p -> p.getReservationDate().isAfter(reservationDateEnd));
        }
        return reservations;
    }

    @Override
    public Reservation getOne(Long id) {
        return reservationRepository.getOne(id);
    }

    @Override
    public Reservation create(Reservation reservation) {
        reservation.setCollected(false);
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation update(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Boolean delete(Long id) {
        reservationRepository.deleteById(id);
        return true;
    }
}
