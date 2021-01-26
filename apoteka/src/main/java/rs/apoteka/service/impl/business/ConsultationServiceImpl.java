package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Consultation;
import rs.apoteka.entity.business.Examination;
import rs.apoteka.entity.business.WorkingHours;
import rs.apoteka.repository.business.ConsultationRepository;
import rs.apoteka.service.intf.business.ConsultationService;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ConsultationServiceImpl implements ConsultationService {
    @Autowired
    private ConsultationRepository consultationRepository;

    @Override
    public List<Consultation> findAll() {
        return consultationRepository.findAll();
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
