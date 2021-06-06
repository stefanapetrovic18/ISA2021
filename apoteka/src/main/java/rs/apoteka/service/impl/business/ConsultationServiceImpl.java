package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Consultation;
import rs.apoteka.entity.business.Examination;
import rs.apoteka.entity.business.WorkingHours;
import rs.apoteka.entity.user.Patient;
import rs.apoteka.entity.user.Pharmacist;
import rs.apoteka.entity.user.PharmacyAdmin;
import rs.apoteka.exception.AppointmentBookingException;
import rs.apoteka.exception.DataMismatchException;
import rs.apoteka.exception.PatientPenalizedException;
import rs.apoteka.exception.UserNotFoundException;
import rs.apoteka.repository.business.ConsultationRepository;
import rs.apoteka.service.intf.auth.AuthenticationService;
import rs.apoteka.service.intf.business.ConsultationService;
import rs.apoteka.service.intf.business.WorkingHoursService;
import rs.apoteka.service.intf.user.PatientService;
import rs.apoteka.service.intf.user.PharmacistService;
import rs.apoteka.service.intf.user.PharmacyAdminService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ConsultationServiceImpl implements ConsultationService {
    @Autowired
    private ConsultationRepository consultationRepository;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private PharmacistService pharmacistService;
    @Autowired
    private PharmacyAdminService pharmacyAdminService;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private WorkingHoursService workingHoursService;

    @Override
    public List<Consultation> findAll() {
        if (authenticationService.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PATIENT"))) {
            Patient patient = patientService.findByUsername(authenticationService.getUsername());
            if (patient != null) {
                return patient.getConsultations();
            }
        } else if (authenticationService.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PHARMACY_ADMIN"))) {
            PharmacyAdmin admin = pharmacyAdminService.findByUsername(authenticationService.getUsername());
            if (admin != null) {
                return admin.getPharmacy().getConsultations();
            }
        } else if (authenticationService.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PHARMACIST"))) {
            Pharmacist pharmacist = pharmacistService.findByUsername(authenticationService.getUsername());
            if (pharmacist != null) {
                return pharmacist.getPharmacy().getConsultations();
            }
        }
        return consultationRepository.findAll();
    }

    @Override
    public List<Consultation> findAllParametrized(Long id, LocalDateTime consultationDate, Long pharmacistID, Long pharmacyID,
                                                  Long patientID, Integer duration, Double price, Integer durationFrom,
                                                  Integer durationTo, Double priceFrom, Double priceTo) {
        List<Consultation> consultations = findAll();
        if (id != null) {
            consultations.removeIf(c -> !c.getId().equals(id));
        }
        if (consultationDate != null) {
            consultations.removeIf(c -> !c.getConsultationDate().equals(consultationDate));
        }
        if (pharmacistID != null) {
            consultations.removeIf(c -> !c.getPharmacist().getId().equals(pharmacistID));
        }
        if (pharmacyID != null) {
            consultations.removeIf(c -> !c.getPharmacy().getId().equals(pharmacyID));
        }
        if (patientID != null) {
            consultations.removeIf(c -> !c.getPatient().getId().equals(patientID));
        }
        if (duration != null) {
            consultations.removeIf(c -> !c.getDuration().equals(duration));
        }
        if (price != null) {
            consultations.removeIf(c -> !c.getPrice().equals(price));
        }
        if (durationFrom != null) {
            consultations.removeIf(c -> c.getDuration() < durationFrom);
        }
        if (durationTo != null) {
            consultations.removeIf(c -> c.getDuration() > durationTo);
        }
        if (durationFrom != null) {
            consultations.removeIf(c -> c.getDuration() < durationFrom);
        }
        if (durationTo != null) {
            consultations.removeIf(c -> c.getDuration() > durationTo);
        }
        if (priceFrom != null) {
            consultations.removeIf(c -> c.getPrice() > priceFrom);
        }
        if (priceTo != null) {
            consultations.removeIf(c -> c.getPrice() > priceTo);
        }
        return consultations;
    }

    @Override
    public List<Consultation> findAllByPharmacyIDAndDateRange(Long pharmacyID, LocalDate dateFrom, LocalDate dateUntil) {
        List<Consultation> consultations = consultationRepository.findAll();
        if (pharmacyID != null) {
            consultations.removeIf(e -> !e.getPharmacy().getId().equals(pharmacyID));
        }
        if (dateFrom != null) {
            consultations.removeIf(e -> e.getConsultationDate().isBefore(dateFrom.atStartOfDay()));
        }
        if (dateUntil != null) {
            consultations.removeIf(e -> e.getConsultationDate().isAfter(dateUntil.atStartOfDay()));
        }
        consultations.removeIf(e -> e.getConsultationDate().plusMinutes(Long.parseLong(e.getDuration().toString())).isAfter(LocalDateTime.now()));
        return consultations;
    }

    @Override
    public Consultation getOne(Long id) {
        return consultationRepository.getOne(id);
    }

    @Override
    public Consultation create(Consultation consultation) throws AppointmentBookingException {
        if (!appointmentCheck(consultation)) {
            throw new AppointmentBookingException();
        }
        return consultationRepository.save(consultation);
    }

    @Override
    public Consultation reserve(Consultation consultation) throws AppointmentBookingException, PatientPenalizedException, UserNotFoundException {
        Patient patient = patientService.findByUsername(authenticationService.getUsername());
        if (patient == null) {
            throw new UserNotFoundException();
        }
        if (patient.getPoints() >= 3) {
            throw new PatientPenalizedException();
        }
        consultation.setPatient(patient);
        consultation.setPrice(consultation.getPharmacy().getPricelist().getConsultationPrice());
        Consultation c = create(consultation);
        patient.getConsultations().add(c);
        patientService.update(patient);
        System.out.println(patient.getUsername());
        sendMail(c);
        return c;
    }

    @Override
    public Consultation cancel(Consultation consultation) throws Exception {
        Patient patient = patientService.findByUsername(authenticationService.getUsername());
        if (patient == null) {
            throw new UserNotFoundException();
        }
        if (consultation.getConsultationDate().isBefore(LocalDateTime.now().plusHours(24))) {
            throw new Exception("Nemoguće je otkazati konsultaciju manje od 24h pre početka istog.");
        }
        patient.getConsultations().removeIf(e -> e.getId().equals(consultation.getId()));
        patientService.update(patient);
        consultation.setPatient(null);
        return update(consultation);
    }

    private void sendMail(Consultation consultation) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(consultation.getPatient().getUsername());
        email.setSubject("Potvrda rezervacije konsultacije");
        email.setText("Poštovani/a " + consultation.getPatient().getForename() + ",\n\n" +
                "Potvrđujemo vašu rezervaciju konsultacije na datum " + consultation.getConsultationDate().toLocalDate().toString() + ".\n\n" +
                "Pregled počinje u " + consultation.getConsultationDate().toLocalTime().toString() + " sati, a traje " + consultation.getDuration() + " minuta.\n\n" +
                "Za više informacija, molimo vas da kliknete na link ispod.\n\n" +
                "http://localhost:4200/pregled?id=" + consultation.getId() + "\n\n" +
                "Možete se obratiti vašem farmaceutu na email adresu " + consultation.getPharmacist().getUsername() + ".\n\n" +
                "Srdačan pozdrav,\n\n" +
                consultation.getPharmacy().getName());
        mailSender.send(email);
    }

    @Override
    public Consultation update(Consultation consultation) throws AppointmentBookingException {
//        if (!appointmentCheck(consultation)) {
//            throw new AppointmentBookingException();
//        }
        return consultationRepository.save(consultation);
    }

    @Override
    public Boolean delete(Long id) throws DataMismatchException {
        Consultation consultation = getOne(id);
        if (consultation.getPatient() == null && (consultation.getConsultationDate().isAfter(LocalDateTime.now()) ||
                consultation.getConsultationDate().plusMinutes(consultation.getDuration()).isBefore(LocalDateTime.now()))) {
            consultationRepository.deleteById(id);
            return true;
        }
        throw new DataMismatchException("Pregled je u toku, ili je rezervisan.");
    }

    private Boolean appointmentCheck(Consultation consultation) {
        return duringWorkingHours(consultation) &&
                appointmentFree(consultation) &&
                patientHasNoConsultation(consultation) &&
                patientHasNoExamination(consultation);
    }

    // Provera da li je konsultacija u vreme radnih sati farmaceuta.
    private Boolean duringWorkingHours(Consultation consultation) {
        Boolean flag = false;
        DayOfWeek dayOfWeek = consultation.getConsultationDate().getDayOfWeek();
        LocalTime localTimeStart = consultation.getConsultationDate().toLocalTime();
        LocalTime localTimeEnd = consultation.getConsultationDate().plusMinutes(consultation.getDuration()).toLocalTime();
        for (WorkingHours workingHours : workingHoursService.findAllByEmployeeID(consultation.getPharmacist().getId())) {
            if (workingHours.getPharmacy().getId().equals(consultation.getPharmacy().getId())) {
                if (dayOfWeek == workingHours.getDayOfWeek()) {
                    if (localTimeStart.isAfter(workingHours.getShiftStart()) && localTimeEnd.isBefore(workingHours.getShiftEnd())) {
                        flag = true;
                    }
                } // else: continue
            } // else: continue
        }
        return flag;
    }

    // Provera da se termini farmaceuta ne poklapaju sa njegovim prethodno zakazanim terminima.
    private Boolean appointmentFree(Consultation consultation) {
        Boolean flag = false;
        if (consultation.getPharmacist().getConsultations() == null || consultation.getPharmacist().getConsultations().isEmpty()) {
            return true;
        }
        for (Consultation cons : consultation.getPharmacist().getConsultations()) {
            if ((consultation.getConsultationDate().isBefore(cons.getConsultationDate()) &&
                    (consultation.getConsultationDate().plusMinutes(consultation.getDuration()).isBefore(
                            cons.getConsultationDate())
                    ))
                    ||
                    (consultation.getConsultationDate().isAfter(cons.getConsultationDate()) &&
                            (consultation.getConsultationDate().plusMinutes(consultation.getDuration())).isAfter(
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

    // Provera da se termin konsultacije ne poklapa sa pacijentovim prethodno zakazanim konsultacijama.
    private Boolean patientHasNoConsultation(Consultation consultation) {
        Boolean flag = false;
        if (consultation.getPatient().getConsultations() == null || consultation.getPatient().getConsultations().isEmpty()) {
            return true;
        }
        for (Consultation cons : consultation.getPatient().getConsultations()) {
            if ((consultation.getConsultationDate().isBefore(cons.getConsultationDate()) &&
                    (consultation.getConsultationDate().plusMinutes(consultation.getDuration()).isBefore(
                            cons.getConsultationDate())
                    ))
                    ||
                    (consultation.getConsultationDate().isAfter(cons.getConsultationDate()) &&
                            (consultation.getConsultationDate().plusMinutes(consultation.getDuration())).isAfter(
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

    // Provera da se termin konsultacije ne poklapa sa pacijentovim prethodno zakazanim pregledima.
    private Boolean patientHasNoExamination(Consultation consultation) {
        Boolean flag = false;
        if (consultation.getPatient().getExaminations() == null || consultation.getPatient().getExaminations().isEmpty()) {
            return true;
        }
        for (Examination exam : consultation.getPatient().getExaminations()) {
            if ((consultation.getConsultationDate().isBefore(exam.getExaminationDate()) &&
                    (consultation.getConsultationDate().plusMinutes(consultation.getDuration()).isBefore(
                            exam.getExaminationDate())
                    ))
                    ||
                    (consultation.getConsultationDate().isAfter(exam.getExaminationDate()) &&
                            (consultation.getConsultationDate().plusMinutes(consultation.getDuration())).isAfter(
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
    private Boolean freeNow(Consultation consultation) {
        Boolean flag = false;
        if (consultation.getPatient().getConsultations() == null || consultation.getPatient().getConsultations().isEmpty()) {
            return true;
        }
        for (Consultation cons : consultation.getPatient().getConsultations()) {
            if ((consultation.getConsultationDate().isBefore(cons.getConsultationDate()) &&
                    (consultation.getConsultationDate().plusMinutes(consultation.getDuration()).isBefore(
                            LocalDateTime.now())
                    ))
                    ||
                    (consultation.getConsultationDate().isAfter(cons.getConsultationDate()) &&
                            (consultation.getConsultationDate().plusMinutes(consultation.getDuration())).isAfter(
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
