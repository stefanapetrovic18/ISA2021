package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Consultation;
import rs.apoteka.entity.business.Examination;
import rs.apoteka.entity.business.WorkingHours;
import rs.apoteka.repository.business.ExaminationRepository;
import rs.apoteka.service.intf.business.ExaminationService;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ExaminationServiceImpl implements ExaminationService {
    @Autowired
    private ExaminationRepository examinationRepository;

    @Override
    public List<Examination> findAll() {
        return examinationRepository.findAll();
    }

    @Override
    public Examination getOne(Long id) {
        return examinationRepository.getOne(id);
    }

    @Override
    public Examination create(Examination examination) {
        if (!appointmentCheck(examination)) {
            return null;
        }
        return examinationRepository.save(examination);
    }

    @Override
    public Examination update(Examination examination) {
        if (!appointmentCheck(examination)) {
            return null;
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
        if (duringWorkingHours(examination) &&
                appointmentFree(examination) &&
                !patientHasConsultation(examination) &&
                !patientHasExamination(examination)) {
            return true;
        }
        return false;
    }

    private Boolean duringWorkingHours(Examination examination) {
        Boolean flag = false;
        DayOfWeek dayOfWeek = examination.getExaminationDate().getDayOfWeek();
        LocalTime localTimeStart = examination.getExaminationDate().toLocalTime();
        LocalTime localTimeEnd = examination.getExaminationDate().plusMinutes(examination.getDuration()).toLocalTime();
        for (WorkingHours workingHours : examination.getDermatologist().getWorkingHours()) {
            if (workingHours.getPharmacy().equals(examination.getPharmacy())) {
                if (dayOfWeek == workingHours.getDayOfWeek()) {
                    if (localTimeStart.isAfter(workingHours.getShiftStart()) && localTimeEnd.isBefore(workingHours.getShiftEnd())) {
                        flag = true;
                    }
                } // else: continue
            } // else: continue
        }
        return flag;
    }

    private Boolean appointmentFree(Examination examination) {
        Boolean flag = false;
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

    private Boolean patientHasConsultation(Examination examination) {
        Boolean flag = false;
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

    private Boolean patientHasExamination(Examination examination) {
        Boolean flag = false;
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
