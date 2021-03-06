package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Reservation;
import rs.apoteka.entity.user.Patient;
import rs.apoteka.exception.DataMismatchException;
import rs.apoteka.exception.PatientPenalizedException;
import rs.apoteka.exception.UserNotFoundException;
import rs.apoteka.repository.business.ReservationRepository;
import rs.apoteka.service.intf.auth.AuthenticationService;
import rs.apoteka.service.intf.business.ReservationService;
import rs.apoteka.service.intf.business.StockpileService;
import rs.apoteka.service.intf.user.PatientService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private StockpileService stockpileService;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public List<Reservation> findAll() {
        if (authenticationService.getAuthentication() != null && authenticationService.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PATIENT"))) {
            Patient patient = patientService.findByUsername(authenticationService.getUsername());
            if (patient == null) {
                return null;
            }
            return reservationRepository.findAll().stream().filter(r -> r.getPatient() != null && r.getPatient().getUsername().equals(authenticationService.getUsername())).collect(Collectors.toList());
        }
        return reservationRepository.findAll();
    }

    @Override
    public List<Reservation> findAllParametrized(Long id, LocalDateTime reservationDate, LocalDateTime reservationDateStart,
                                                 LocalDateTime reservationDateEnd, Long pharmacyID, Long medicineID,
                                                 Long patientID, Boolean collected, String reservationNumber,
                                                 Boolean nonCollected) {
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
            reservations.removeIf(p -> p.getPatient() == null || !p.getPatient().getId().equals(patientID));
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
        if (nonCollected != null && nonCollected) {
            reservations.removeIf(p -> p.getCollectionDate() != null && p.getCollectionDate().isAfter(LocalDateTime.now()));
        }
        return reservations;
    }

    @Override
    public List<Reservation> findAllByPharmacyIDAndDateRange(Long pharmacyID, LocalDate dateFrom, LocalDate dateUntil) {
        List<Reservation> reservations = findAll();
        if (pharmacyID != null) {
            reservations.removeIf(e -> !e.getPharmacy().getId().equals(pharmacyID));
        }
        if (dateFrom != null) {
            reservations.removeIf(e -> e.getReservationDate().isBefore(dateFrom.atStartOfDay()));
        }
        if (dateUntil != null) {
            reservations.removeIf(e -> e.getReservationDate().isAfter(dateUntil.atStartOfDay()));
        }
        reservations.removeIf(e -> !e.getCollected());
        return reservations;
    }

    @Override
    public Reservation getOne(Long id) {
        return reservationRepository.getOne(id);
    }

    @Override
    public Reservation create(Reservation reservation) {
        reservation.setCollected(false);
        reservation.setPenalized(false);
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation reserve(Reservation reservation) throws PatientPenalizedException {
        Patient patient = patientService.findByUsername(authenticationService.getUsername());
        if (patient == null) {
            return null;
        }
        if (patient.getPoints() >= 3) {
            throw new PatientPenalizedException();
        }
        reservation.setReservationNumber(String.valueOf(new Random().nextLong()));
        reservation.setPatient(patient);
        reservation.getPharmacy().getStockpile().forEach(i -> {
            if (i.getMedicine().getId().equals(reservation.getMedicine().getId())) {
                if (i.getQuantity() == 0) {
                    try {
                        throw new Exception("Odabrani lek nije na stanju!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                i.setQuantity(i.getQuantity() - 1);
                try {
                    stockpileService.update(i);
                } catch (UserNotFoundException | DataMismatchException e) {
                    e.printStackTrace();
                }
            }
        });
        Reservation r = create(reservation);
//        patient.().add(r);
        patientService.update(patient);
        sendMail(r);
        return r;
    }

    @Override
    public Reservation cancel(Reservation reservation) throws Exception {
        if (reservation.getCollectionDate().isBefore(LocalDateTime.now().plusHours(24)) || reservation.getCollected()) {
            throw new Exception("Nemoguće je otkazati rezervaciju leka manje od 24h pre preuzimanja istog.");
        }
//        reservation.getPatient().getExaminations().removeIf(e -> e.getId().equals(reservation.getId()));
        reservation.getPharmacy().getStockpile().forEach(i -> {
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
