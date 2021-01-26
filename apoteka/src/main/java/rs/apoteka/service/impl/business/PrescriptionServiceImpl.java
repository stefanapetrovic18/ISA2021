package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Prescription;
import rs.apoteka.repository.business.PrescriptionRepository;
import rs.apoteka.service.intf.business.PrescriptionService;

import java.util.List;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Override
    public List<Prescription> findAll() {
        return prescriptionRepository.findAll();
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
