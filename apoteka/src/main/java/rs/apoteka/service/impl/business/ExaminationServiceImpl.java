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
import rs.apoteka.exception.DataMismatchException;
import rs.apoteka.exception.PatientPenalizedException;
import rs.apoteka.repository.business.ExaminationRepository;
import rs.apoteka.service.intf.auth.AuthenticationService;
import rs.apoteka.service.intf.business.ExaminationService;
import rs.apoteka.service.intf.business.WorkingHoursService;
import rs.apoteka.service.intf.user.DermatologistService;
import rs.apoteka.service.intf.user.PatientService;
import rs.apoteka.service.intf.user.PharmacyAdminService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
    private WorkingHoursService workingHoursService;
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
        return examinationRepository.findAll().stream().filter(e -> e.getPatient() == null).collect(Collectors.toList())
                .stream().filter(e -> e.getExaminationDate().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
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
            examinations.removeIf(e -> !e.getExaminationDate().equals(examinationDate));
        }
        if (dermatologistID != null) {
            examinations.removeIf(e -> !e.getDermatologist().getId().equals(dermatologistID));
        }
        if (pharmacyID != null) {
            examinations.removeIf(e -> !e.getPharmacy().getId().equals(pharmacyID));
        }
        if (patientID != null) {
            examinations.removeIf(e -> !e.getPatient().getId().equals(patientID));
        }
        if (duration != null) {
            examinations.removeIf(e -> !e.getDuration().equals(duration));
        }
        if (price != null) {
            examinations.removeIf(e -> !e.getPrice().equals(price));
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
//        if (!dermatologistCheck(examination)) {
//            throw new AppointmentBookingException();
//        }
        Dermatologist dermatologist = dermatologistService.getOne(examination.getDermatologist().getId());
        if (examination.getPrice() == null || examination.getPrice() == 0) {
            examination.setPrice(examination.getPharmacy().getPricelist().getExaminationPrice() * examination.getDuration());
        }
        if (dermatologist.getAppointments() == null) {
            dermatologist.setAppointments(new ArrayList<>());
        }
        examination.setDermatologist(dermatologist);
        if (!dermatologistCheck(examination)) {
            throw new AppointmentBookingException();
        }
        Examination e = examinationRepository.save(examination);
        dermatologist.getAppointments().add(e);
        dermatologistService.update(dermatologist);
        return e;
    }

    @Override
    public Examination quickReserve(Examination examination) throws AppointmentBookingException, PatientPenalizedException {
        Patient patient = patientService.findByUsername(authenticationService.getUsername());
        if (patient == null) {
            return null;
        }
        if (patient.getPoints() >= 3) {
            throw new PatientPenalizedException();
        }
        if (!patientHasNoConsultation(examination, patient) || !patientHasNoExamination(examination, patient)) {
            throw new AppointmentBookingException();
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
//        if (!appointmentCheck(examination)) {
//            throw new AppointmentBookingException();
//        }
        return examinationRepository.save(examination);
    }

    @Override
    public Boolean delete(Long id) throws DataMismatchException {
        Examination examination = getOne(id);
        if (examination.getPatient() == null && (examination.getExaminationDate().isAfter(LocalDateTime.now()) ||
                examination.getExaminationDate().plusMinutes(examination.getDuration()).isBefore(LocalDateTime.now()))) {
            examinationRepository.deleteById(id);
            return true;
        }
        throw new DataMismatchException("Pregled je u toku, ili je rezervisan.");
    }

    private Boolean appointmentCheck(Examination examination, Patient patient) {
        return duringWorkingHours(examination) &&
                appointmentFree(examination) &&
                patientHasNoConsultation(examination, patient) &&
                patientHasNoExamination(examination, patient);
    }

    private Boolean dermatologistCheck(Examination examination) {
        return duringWorkingHours(examination) && appointmentFree(examination);
    }

    // Provera da li je pregled za vreme radnih sati dermatologa.
    private Boolean duringWorkingHours(Examination examination) {
        System.out.println("Za vreme radnih sati:");
        Boolean flag = false;
        DayOfWeek dayOfWeek = examination.getExaminationDate().getDayOfWeek();
        LocalTime localTimeStart = examination.getExaminationDate().toLocalTime();
        LocalTime localTimeEnd = examination.getExaminationDate().plusMinutes(examination.getDuration()).toLocalTime();
        System.out.println(dayOfWeek.toString());
        System.out.println(localTimeStart.toString());
        System.out.println(localTimeEnd.toString());
        for (WorkingHours workingHours : workingHoursService.findAllByEmployeeID(examination.getDermatologist().getId())) {
            System.out.println(workingHours.getPharmacy().getId() + " ?= " + examination.getPharmacy().getId());
            if (workingHours.getPharmacy().getId().equals(examination.getPharmacy().getId())) {
                System.out.println("----------------");
                System.out.println(workingHours.getDayOfWeek().toString());
                System.out.println(workingHours.getShiftStart().toString());
                System.out.println(workingHours.getShiftEnd().toString());
                if (dayOfWeek == workingHours.getDayOfWeek()) {
                    if (localTimeStart.isAfter(workingHours.getShiftStart()) && localTimeEnd.isBefore(workingHours.getShiftEnd())) {
                        flag = true;
                    }
                } // else: continue
            } // else: continue
        }
        System.out.println(flag);
        return flag;
    }

    // Provera da se termini dermatologa ne poklapaju sa njegovim prethodno zakazanim pregledima.
    private Boolean appointmentFree(Examination examination) {
        System.out.println("Slobodan termin (dermatolog):");
        Boolean flag = false;
        if (examination.getDermatologist().getAppointments() == null || examination.getDermatologist().getAppointments().isEmpty()) {
            System.out.println("C1: " + true);
            return true;
        }
        for (Examination exam : examination.getDermatologist().getAppointments()) {
            if ((examination.getExaminationDate().isBefore(exam.getExaminationDate()) &&
                    (examination.getExaminationDate().plusMinutes(examination.getDuration()).isBefore(
                            exam.getExaminationDate())
                    ))
                    ||
                    (examination.getExaminationDate().isAfter(exam.getExaminationDate().plusMinutes(exam.getDuration())) &&
                            (examination.getExaminationDate().plusMinutes(examination.getDuration())).isAfter(
                                    exam.getExaminationDate()))) {
                // Termini se ne poklapaju.
                flag = true;
            } else {
                // Termini se poklapaju, greska.
                System.out.println("C2: " + false);
                return false;
            }
        }
        System.out.println("EXIT: " + flag);
        return flag;
    }

    // Provera da se termin pregleda ne poklapa sa pacijentovim prethodno zakazanim konsultacijama.
    private Boolean patientHasNoConsultation(Examination examination, Patient patient) {
        System.out.println("Slobodan termin (savetovanje):");
        Boolean flag = false;
        if (patient.getConsultations() == null || patient.getConsultations().isEmpty()) {
            System.out.println("C1: " + true);
            return true;
        }
        System.out.println("SIZE: " + patient.getExaminations().size());
        for (Consultation cons : patient.getConsultations()) {
            if ((examination.getExaminationDate().isBefore(cons.getConsultationDate()) &&
                    (examination.getExaminationDate().plusMinutes(examination.getDuration()).isBefore(
                            cons.getConsultationDate())
                    ))
                    ||
                    (examination.getExaminationDate().isAfter(cons.getConsultationDate().plusMinutes(cons.getDuration())) &&
                            (examination.getExaminationDate().plusMinutes(examination.getDuration())).isAfter(
                                    cons.getConsultationDate()))) {
                // Termini se ne poklapaju.
                flag = true;
            } else {
                // Termini se poklapaju, greska.
                System.out.println("C2: " + false);
                return false;
            }
        }
        System.out.println("EXIT: " + flag);
        return flag;
    }

    // Provera da se termin pregleda ne poklapa sa pacijentovim prethodno zakazanim pregledima.
    private Boolean patientHasNoExamination(Examination examination, Patient patient) {
        System.out.println("Slobodan termin (pregled, pacijent):");
        Boolean flag = false;
        if (patient.getExaminations() == null || patient.getExaminations().isEmpty()) {
            System.out.println("C1: " + true);
            return true;
        }
        System.out.println("SIZE: " + patient.getExaminations().size());
        for (Examination exam : patient.getExaminations()) {
            if ((examination.getExaminationDate().isBefore(exam.getExaminationDate()) &&
                    (examination.getExaminationDate().plusMinutes(examination.getDuration()).isBefore(
                            exam.getExaminationDate())
                    ))
                    ||
                    (examination.getExaminationDate().isAfter(exam.getExaminationDate().plusMinutes(exam.getDuration())) &&
                            (examination.getExaminationDate().plusMinutes(examination.getDuration())).isAfter(
                                    exam.getExaminationDate()))) {
                // Termini se ne poklapaju.
                flag = true;
            } else {
                // Termini se poklapaju, greska.
                System.out.println("C2: " + false);
                return false;
            }
        }
        System.out.println("EXIT: " + flag);
        return flag;
    }

    // Provera da li je pregled u toku.
    private Boolean patientFreeNow(Examination examination) {
        System.out.println("Pregled u toku (pacijent):");
        Boolean flag = false;
        if (examination.getPatient() == null) {
            System.out.println("C1: " + true);
            return true;
        }
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
                System.out.println("C2: " + false);
                return false;
            }
        }
        System.out.println("EXIT: " + flag);
        return flag;
    }

    // Provera da li je pregled u toku.
    private Boolean dermatologistFreeNow(Examination examination) {
        System.out.println("Pregled u toku (dermatolog):");
        Boolean flag = false;
        for (Examination exam : examination.getDermatologist().getAppointments()) {
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
                System.out.println("C2: " + false);
                return false;
            }
        }
        System.out.println("EXIT: " + flag);
        return flag;
    }
}
