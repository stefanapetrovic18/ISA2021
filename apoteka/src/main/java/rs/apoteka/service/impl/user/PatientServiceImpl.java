package rs.apoteka.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Medicine;
import rs.apoteka.entity.user.Patient;
import rs.apoteka.repository.user.PatientRepository;
import rs.apoteka.service.intf.user.PatientService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public Patient findByUsername(String username) {
        return patientRepository.findByUsername(username);
    }

    @Override
    public List<Patient> findAllParametrized(Long id, Long allergyMedicineID, Long consultationID,
                                             Long examinationID, Integer points, Integer pointsFrom, Integer pointsTo) {
        List<Patient> patients = findAll();
        if (id != null) {
            patients.removeIf(p -> !p.getId().equals(id));
        }
        if (consultationID != null) {
            patients = patients.stream().filter(p -> p.getConsultations().removeIf(ph -> !ph.getId().equals(consultationID))).collect(Collectors.toList());
        }
        if (examinationID != null) {
            patients = patients.stream().filter(p -> p.getExaminations().removeIf(ph -> !ph.getId().equals(examinationID))).collect(Collectors.toList());
        }
        if (allergyMedicineID != null) {
            patients = patients.stream().filter(p -> p.getAllergies().removeIf(ph -> !ph.getId().equals(allergyMedicineID))).collect(Collectors.toList());
        }
        if (points != null) {
            patients.removeIf(p -> !p.getPoints().equals(points));
        }
        if (pointsFrom != null) {
            patients.removeIf(p -> p.getPoints() < pointsFrom);
        }
        if (pointsTo != null) {
            patients.removeIf(p -> p.getPoints() > pointsTo);
        }
        return patients;
    }

    @Override
    public Patient getOne(Long id) {
        return patientRepository.getOne(id);
    }

    @Override
    public Patient create(Patient patient) {
        patient.setPoints(0);
        return patientRepository.save(patient);
    }

    @Override
    public Patient update(Patient patient) {
        Patient p = getOne(patient.getId());
        if (p == null) {
            return null;
        }
        p.setAllergies(patient.getAllergies());
//        if (patient.getAllergies() != null && patient.getAllergies().size() > 0) {
//            for (Medicine a : patient.getAllergies()) {
//                boolean flag = true;
//                if (p.getAllergies() != null && p.getAllergies().size() > 0) {
//                    for (Medicine b : p.getAllergies()) {
//                        if (b.getId().equals(a.getId())) {
//                            flag = false;
//                            break;
//                        }
//                    }
//                } else {
//                    p.setAllergies(new ArrayList<>());
//                }
//                if (flag) {
//                    p.getAllergies().add(a);
//                }
//            }
//        }
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        patient.setPassword(encoder.encode(patient.getPassword()));
        return patientRepository.save(p);
    }

    @Override
    public Boolean delete(Long id) {
        patientRepository.deleteById(id);
        return true;
    }
}
