package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Consultation;
import rs.apoteka.entity.business.Examination;
import rs.apoteka.entity.business.WorkingHours;
import rs.apoteka.entity.user.Patient;
import rs.apoteka.repository.business.ConsultationRepository;
import rs.apoteka.service.intf.auth.AuthenticationService;
import rs.apoteka.service.intf.business.ConsultationService;
import rs.apoteka.service.intf.user.PatientService;

import java.time.DayOfWeek;
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
    private JavaMailSender mailSender;

    @Override
    public List<Consultation> findAll() {
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
    public Consultation getOne(Long id) {
        return consultationRepository.getOne(id);
    }

    @Override
    public Consultation create(Consultation consultation) {
        if (!appointmentCheck(consultation)) {
            return null;
        }
        return consultationRepository.save(consultation);
    }

    @Override
    public Consultation reserve(Consultation consultation) {
        Patient patient = patientService.findByUsername(authenticationService.getUsername());
        if (patient == null) {
            return null;
        }
        consultation.setPatient(patient);
        Consultation c = update(consultation);
        patient.getConsultations().add(c);
        patientService.update(patient);
        sendMail(c);
        return c;
    }

    @Override
    public Consultation cancel(Consultation consultation) throws Exception {
        if (consultation.getConsultationDate().isBefore(LocalDateTime.now().plusHours(24))) {
            throw new Exception("Nemoguće je otkazati konsultaciju manje od 24h pre početka istog.");
        }
        consultation.getPatient().getConsultations().removeIf(e -> e.getId().equals(consultation.getId()));
        patientService.update(consultation.getPatient());
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
    public Consultation update(Consultation consultation) {
        if (!appointmentCheck(consultation)) {
            return null;
        }
        return consultationRepository.save(consultation);
    }

    @Override
    public Boolean delete(Long id) {
        Consultation consultation = getOne(id);
        if (!freeNow(consultation)) {
            return false;
        }
        consultationRepository.deleteById(id);
        return true;
    }

    private Boolean appointmentCheck(Consultation consultation) {
        return duringWorkingHours(consultation) &&
                appointmentFree(consultation) &&
                !patientHasConsultation(consultation) &&
                !patientHasExamination(consultation);
    }

    private Boolean duringWorkingHours(Consultation consultation) {
        Boolean flag = false;
        DayOfWeek dayOfWeek = consultation.getConsultationDate().getDayOfWeek();
        LocalTime localTimeStart = consultation.getConsultationDate().toLocalTime();
        LocalTime localTimeEnd = consultation.getConsultationDate().plusMinutes(consultation.getDuration()).toLocalTime();
        for (WorkingHours workingHours : consultation.getPharmacist().getWorkingHours()) {
            if (workingHours.getPharmacy().equals(consultation.getPharmacy())) {
                if (dayOfWeek == workingHours.getDayOfWeek()) {
                    if (localTimeStart.isAfter(workingHours.getShiftStart()) && localTimeEnd.isBefore(workingHours.getShiftEnd())) {
                        flag = true;
                    }
                } // else: continue
            } // else: continue
        }
        return flag;
    }

    private Boolean appointmentFree(Consultation consultation) {
        Boolean flag = false;
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

    private Boolean patientHasConsultation(Consultation consultation) {
        Boolean flag = false;
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

    private Boolean patientHasExamination(Consultation consultation) {
        Boolean flag = false;
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

    private Boolean freeNow(Consultation consultation) {
        Boolean flag = false;
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
