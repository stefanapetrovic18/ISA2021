package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Prescription;
import rs.apoteka.repository.business.PrescriptionRepository;
import rs.apoteka.service.intf.business.PrescriptionService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Override
    public List<Prescription> findAll() {
        return prescriptionRepository.findAll();
    }

    @Override
    public List<Prescription> findAllParametrized(Long id, String code, LocalDateTime issueDate, LocalDateTime issueDateStart,
                                                  LocalDateTime issueDateEnd, Long pharmacyID, Long patientID, Long medicineID) {
        List<Prescription> prescriptions = findAll();
        if (id != null) {
            prescriptions.removeIf(p -> !p.getId().equals(id));
        }
        if (code != null) {
            prescriptions.removeIf(p-> !p.getCode().contains(code));
        }
        if (issueDate != null) {
            prescriptions.removeIf(p -> !p.getIssueDate().equals(issueDate));
        }
        if (issueDateStart != null) {
            prescriptions.removeIf(p -> p.getIssueDate().isBefore(issueDateStart));
        }
        if (issueDateEnd != null) {
            prescriptions.removeIf(p -> p.getIssueDate().isAfter(issueDateEnd));
        }
        if (pharmacyID != null) {
            prescriptions.removeIf(p -> !p.getPharmacy().getId().equals(pharmacyID));
        }
        if (patientID != null) {
            prescriptions.removeIf(p -> !p.getPatient().getId().equals(patientID));
        }
        if (medicineID != null) {
            prescriptions = prescriptions.stream().filter(p -> p.getMedicines().removeIf(m -> !m.getId().equals(medicineID))).collect(Collectors.toList());
        }
        return prescriptions;
    }

    @Override
    public Prescription getOne(Long id) {
        return prescriptionRepository.getOne(id);
    }

    @Override
    public Prescription create(Prescription prescription) {
        Prescription presc = checkForAllergies(prescription);
        return prescriptionRepository.save(presc);
    }

    @Override
    public Prescription update(Prescription prescription) {
        Prescription presc = checkForAllergies(prescription);
        return prescriptionRepository.save(presc);
    }

    private Prescription checkForAllergies(Prescription prescription) {
//        prescription.getMedicines().forEach(medicine -> {
//            if (prescription.getPatient().getAllergies().contains(medicine)) {
//                prescription.getMedicines().remove(medicine);
//            }
//        });
        // Ukoliko je pacijent alergican na lek, ukloni ga iz recepta.
        prescription.getMedicines().removeIf(medicine -> prescription.getPatient().getAllergies().contains(medicine));
        return prescription;
    }

    @Override
    public Boolean delete(Long id) {
        prescriptionRepository.deleteById(id);
        return true;
    }
}
