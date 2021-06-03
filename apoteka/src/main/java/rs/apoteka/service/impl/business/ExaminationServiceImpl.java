package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Consultation;
import rs.apoteka.entity.business.Examination;
import rs.apoteka.entity.business.WorkingHours;
import rs.apoteka.entity.user.Dermatologist;
import rs.apoteka.entity.user.Patient;
import rs.apoteka.entity.user.PharmacyAdmin;
import rs.apoteka.exception.AppointmentBookingException;
import rs.apoteka.repository.business.ExaminationRepository;
import rs.apoteka.service.intf.auth.AuthenticationService;
import rs.apoteka.service.intf.business.ExaminationService;
import rs.apoteka.service.intf.user.DermatologistService;
import rs.apoteka.service.intf.user.PatientService;
import rs.apoteka.service.intf.user.PharmacyAdminService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExaminationServiceImpl implements ExaminationService {
    @Autowired
    private ExaminationRepository examinationRepository;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private PharmacyAdminService pharmacyAdminService;
    @Autowired
    private DermatologistService dermatologistService;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public List<Examination> findAll() {
        if (authenticationService.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PATIENT"))) {
            Patient patient = patientService.findByUsername(authenticationService.getUsername());
            if (patient != null) {
                List<Examination> examinations = findAllFree();
                examinations.addAll(patient.getExaminations());
                return examinations;
            }
        } else if (authenticationService.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PHARMACY_ADMIN"))) {
            PharmacyAdmin admin = pharmacyAdminService.findByUsername(authenticationService.getUsername());
            if (admin != null) {
                return admin.getPharmacy().getExaminations();
            }
        } else if (authenticationService.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PHARMACIST"))) {
            Dermatologist dermatologist = dermatologistService.findByUsername(authenticationService.getUsername());
            if (dermatologist != null) {
                return dermatologist.getAppointments();
            }
        }
        return examinationRepository.findAll();
    }

    @Override
    public List<Examination> findAllFree() {
        return examinationRepository.findAll().stream().filter(e -> e.getPatient() == null).collect(Collectors.toList());
    }

    @Override
    public List<Examination> findAllReserved() {
        return examinationRepository.findAll().stream().filter(e -> e.getPatient() != null).collect(Collectors.toList());
    }

    @Override
    public List<Examination> findAllParametrized(Long id, LocalDateTime examinationDate, Long dermatologistID, Long pharmacyID,
                                                 Long patientID, Integer duration, Double price, Integer durationFrom,
                                                 Integer durationTo, Double priceFrom, Double priceTo, Boolean quickReservation) {
        List<Examination> examinations = findAll();
        if (id != null) {
            examinations.removeIf(e -> !e.getId().equals(id));
        }
        if (examinationDate != null) {
            examinations.removeIf(e -> e.getExaminationDate().equals(examinationDate));
        }
        if (dermatologistID != null) {
            examinations.removeIf(e -> e.getDermatologist().getId().equals(dermatologistID));
        }
        if (pharmacyID != null) {
            examinations.removeIf(e -> e.getPharmacy().getId().equals(pharmacyID));
        }
        if (patientID != null) {
            examinations.removeIf(e -> e.getPatient().getId().equals(patientID));
        }
        if (duration != null) {
            examinations.removeIf(e -> e.getDuration().equals(duration));
        }
        if (price != null) {
            examinations.removeIf(e -> e.getPrice().equals(price));
        }
        if (durationFrom != null) {
            examinations.removeIf(e -> e.getDuration() < durationFrom);
        }
        if (durationTo != null) {
            examinations.removeIf(e -> e.getDuration() > durationTo);
        }
        if (durationFrom != null) {
            examinations.removeIf(e -> e.getDuration() < durationFrom);
        }
        if (durationTo != null) {
            examinations.removeIf(e -> e.getDuration() > durationTo);
        }
        if (priceFrom != null) {
            examinations.removeIf(e -> e.getPrice() > priceFrom);
        }
        if (priceTo != null) {
            examinations.removeIf(e -> e.getPrice() > priceTo);
        }
        if (quickReservation != null) {
            examinations.removeIf(e -> e.getQuickReservation() != quickReservation);
        }
        return examinations;
    }

    @Override
    public List<Examination> findAllByPharmacyIDAndDateRange(Long pharmacyID, LocalDate dateFrom, LocalDate dateUntil) {
        List<Examination> examinations = examinationRepository.findAll();
        if (pharmacyID != null) {
            examinations.removeIf(e -> !e.getPharmacy().getId().equals(pharmacyID));
        }
        if (dateFrom != null) {
            examinations.removeIf(e -> e.getExaminationDate().isBefore(dateFrom.atStartOfDay()));
        }
        if (dateUntil != null) {
            examinations.removeIf(e -> e.getExaminationDate().isAfter(dateUntil.atStartOfDay()));
        }
        examinations.removeIf(e -> e.getExaminationDate().plusMinutes(Long.parseLong(e.getDuration().toString())).isAfter(LocalDateTime.now()));
        return examinations;
    }

    @Override
    public Examination getOne(Long id) {
        return examinationRepository.getOne(id);
    }

    @Override
    public Examination create(Examination examination) throws AppointmentBookingException {
        if (!appointmentCheck(examination)) {
            throw new AppointmentBookingException();
        }
        return examinationRepository.save(examination);
    }

    @Override
    public Examination quickReserve(Examination examination) throws AppointmentBookingException {
        Patient patient = patientService.findByUsername(authenticationService.getUsername());
        if (patient == null) {
            return null;
        }
        examination.setPatient(patient);
        Examination e = update(examination);
        patient.getExaminations().add(e);
        patientService.update(patient);
        sendMail(e);
        return e;
    }

    @Override
    public Examination cancel(Examination examination) throws Exception {
        if (examination.getExaminationDate().isBefore(LocalDateTime.now().plusHours(24))) {
            throw new Exception("Nemoguće je otkazati pregled manje od 24h pre početka istog.");
        }
        Patient patient = patientService.findByUsername(authenticationService.getUsername());
        patient.getExaminations().removeIf(e -> e.getId().equals(examination.getId()));
        patientService.update(patient);
        examination.setPatient(null);
        return update(examination);
    }

    private void sendMail(Examination examination) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(examination.getPatient().getUsername());
        email.setSubject("Potvrda rezervacije pregleda");
        email.setText("Poštovani/a " + examination.getPatient().getForename() + ",\n\n" +
                "Potvrđujemo vašu rezervaciju pregleda na datum " + examination.getExaminationDate().toLocalDate().toString() + ".\n\n" +
                "Pregled počinje u " + examination.getExaminationDate().toLocalTime().toString() + " sati, a traje " + examination.getDuration() + " minuta.\n\n" +
                "Za više informacija, molimo vas da kliknete na link ispod.\n\n" +
                "http://localhost:4200/pregled?id=" + examination.getId() + "\n\n" +
                "Možete se obratiti vašem dermatologu na email adresu " + examination.getDermatologist().getUsername() + ".\n\n" +
                "Srdačan pozdrav,\n\n" +
                examination.getPharmacy().getName());
        mailSender.send(email);
    }

    @Override
    public Examination update(Examination examination) throws AppointmentBookingException {
        if (!appointmentCheck(examination)) {
            throw new AppointmentBookingException();
        }
        return examinationRepository.save(examination);
    }

    @Override
    public Boolean delete(Long id) {
        Examination examination = getOne(id);
        if (!freeNow(examination)) {
            return false;
        }
        examinationRepository.deleteById(id);
        return true;
    }

    private Boolean appointmentCheck(Examination examination) {
        return duringWorkingHours(examination) &&
                appointmentFree(examination) &&
                patientHasNoConsultation(examination) &&
                patientHasNoExamination(examination);
    }

    // Provera da li je pregled za vreme radnih sati dermatologa.
    private Boolean duringWorkingHours(Examination examination) {
        Boolean flag = false;
        DayOfWeek dayOfWeek = examination.getExaminationDate().getDayOfWeek();
        LocalTime localTimeStart = examination.getExaminationDate().toLocalTime();
        LocalTime localTimeEnd = examination.getExaminationDate().plusMinutes(examination.getDuration()).toLocalTime();
        for (WorkingHours workingHours : examination.getDermatologist().getWorkingHours()) {
            if (workingHours.getPharmacy().getId().equals(examination.getPharmacy().getId())) {
                if (dayOfWeek == workingHours.getDayOfWeek()) {
                    if (localTimeStart.isAfter(workingHours.getShiftStart()) && localTimeEnd.isBefore(workingHours.getShiftEnd())) {
                        flag = true;
                    }
                } // else: continue
            } // else: continue
        }
        return flag;
    }

    // Provera da se termini dermatologa ne poklapaju sa njegovim prethodno zakazanim pregledima.
    private Boolean appointmentFree(Examination examination) {
        Boolean flag = false;
        if (examination.getDermatologist().getAppointments() == null || examination.getDermatologist().getAppointments().isEmpty()) {
            return true;
        }
        for (Examination exam : examination.getDermatologist().getAppointments()) {
            if ((examination.getExaminationDate().isBefore(exam.getExaminationDate()) &&
                    (examination.getExaminationDate().plusMinutes(examination.getDuration()).isBefore(
                            exam.getExaminationDate())
                    ))
                    ||
                    (examination.getExaminationDate().isAfter(exam.getExaminationDate()) &&
                            (examination.getExaminationDate().plusMinutes(examination.getDuration())).isAfter(
                                    exam.getExaminationDate()))) {
                // Termini se ne poklapaju.
                flag = true;
            } else {
                // Termini se poklapaju, greska.
                return false;
            }
        }
        return flag;
    }

    // Provera da se termin pregleda ne poklapa sa pacijentovim prethodno zakazanim konsultacijama.
    private Boolean patientHasNoConsultation(Examination examination) {
        Boolean flag = false;
        if (examination.getPatient().getConsultations() == null || examination.getPatient().getConsultations().isEmpty()) {
            return true;
        }
        for (Consultation cons : examination.getPatient().getConsultations()) {
            if ((examination.getExaminationDate().isBefore(cons.getConsultationDate()) &&
                    (examination.getExaminationDate().plusMinutes(examination.getDuration()).isBefore(
                            cons.getConsultationDate())
                    ))
                    ||
                    (examination.getExaminationDate().isAfter(cons.getConsultationDate()) &&
                            (examination.getExaminationDate().plusMinutes(examination.getDuration())).isAfter(
                                    cons.getConsultationDate()))) {
                // Termini se ne poklapaju.
                flag = true;
            } else {
                // Termini se poklapaju, greska.
                return false;
            }
        }
        return flag;
    }

    // Provera da se termin pregleda ne poklapa sa pacijentovim prethodno zakazanim pregledima.
    private Boolean patientHasNoExamination(Examination examination) {
        Boolean flag = false;
        if (examination.getPatient().getExaminations() == null || examination.getPatient().getExaminations().isEmpty()) {
            return true;
        }
        for (Examination exam : examination.getPatient().getExaminations()) {
            if ((examination.getExaminationDate().isBefore(exam.getExaminationDate()) &&
                    (examination.getExaminationDate().plusMinutes(examination.getDuration()).isBefore(
                            exam.getExaminationDate())
                    ))
                    ||
                    (examination.getExaminationDate().isAfter(exam.getExaminationDate()) &&
                            (examination.getExaminationDate().plusMinutes(examination.getDuration())).isAfter(
                                    exam.getExaminationDate()))) {
                // Termini se ne poklapaju.
                flag = true;
            } else {
                // Termini se poklapaju, greska.
                return false;
            }
        }
        return flag;
    }

    // Provera da li je pregled u toku.
    private Boolean freeNow(Examination examination) {
        Boolean flag = false;
        for (Examination exam : examination.getPatient().getExaminations()) {
            if ((examination.getExaminationDate().isBefore(exam.getExaminationDate()) &&
                    (examination.getExaminationDate().plusMinutes(examination.getDuration()).isBefore(
                            LocalDateTime.now())
                    ))
                    ||
                    (examination.getExaminationDate().isAfter(exam.getExaminationDate()) &&
                            (examination.getExaminationDate().plusMinutes(examination.getDuration())).isAfter(
                                    LocalDateTime.now()))) {
                // Termini se ne poklapaju.
                flag = true;
            } else {
                // Termini se poklapaju, greska.
                return false;
            }
        }
        return flag;
    }
}
