package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Reservation;
import rs.apoteka.entity.user.Patient;
import rs.apoteka.repository.business.ReservationRepository;
import rs.apoteka.service.intf.auth.AuthenticationService;
import rs.apoteka.service.intf.business.ReservationService;
import rs.apoteka.service.intf.user.PatientService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private JavaMailSender mailSender;

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
    public Reservation reserve(Reservation reservation) {
        Patient patient = patientService.findByUsername(authenticationService.getUsername());
        if (patient == null) {
            return null;
        }
        reservation.setPatient(patient);
        reservation.getPharmacy().getPricelist().getItems().forEach(i -> {
            if (i.getMedicine().getId().equals(reservation.getMedicine().getId())) {
                if (i.getQuantity() == 0) {
                    try {
                        throw new Exception("Odabrani lek nije na stanju!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                i.setQuantity(i.getQuantity() - 1);
            }
        });
        Reservation r = update(reservation);
//        patient.().add(r);
        patientService.update(patient);
        sendMail(r);
        return r;
    }

    @Override
    public Reservation cancel(Reservation reservation) throws Exception {
        if (reservation.getReservationDate().isBefore(LocalDateTime.now().plusHours(24))) {
            throw new Exception("Nemoguće je otkazati rezervaciju leka manje od 24h pre preuzimanja istog.");
        }
        reservation.getPatient().getExaminations().removeIf(e -> e.getId().equals(reservation.getId()));
        reservation.getPharmacy().getPricelist().getItems().forEach(i -> {
            if (i.getMedicine().getId().equals(reservation.getMedicine().getId())) {
                i.setQuantity(i.getQuantity() + 1);
            }
        });
        reservation.setPatient(null);
        return update(reservation);
    }

    private void sendMail(Reservation reservation) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(reservation.getPatient().getUsername());
        email.setSubject("Potvrda rezervacije leka");
        email.setText("Poštovani/a " + reservation.getPatient().getForename() + ",\n\n" +
                "Potvrđujemo vašu rezervaciju leka na datum " + reservation.getReservationDate().toLocalDate().toString() + ".\n\n" +
                "Lek morate preuzeti najkasnije do " + reservation.getReservationDate().toLocalTime().toString() + " sati.\n\n" +
                "Za više informacija, molimo vas da kliknete na link ispod.\n\n" +
                "http://localhost:4200/rezervacija?id=" + reservation.getId() + "\n\n" +
                "Jedinstveni broj rezervacije je " + reservation.getReservationNumber() + ".\n\n" +
                "Srdačan pozdrav,\n\n" +
                reservation.getPharmacy().getName());
        mailSender.send(email);
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
