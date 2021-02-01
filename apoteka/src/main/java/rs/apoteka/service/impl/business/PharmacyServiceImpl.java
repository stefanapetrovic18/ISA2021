package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Pharmacy;
import rs.apoteka.entity.user.Pharmacist;
import rs.apoteka.repository.business.PharmacyRepository;
import rs.apoteka.service.intf.business.PharmacyService;

import java.util.List;

@Service
public class PharmacyServiceImpl implements PharmacyService {
    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Override
    public List<Pharmacy> findAll() {
        return pharmacyRepository.findAll();
    }

    @Override
    public Pharmacy getOne(Long id) {
        return pharmacyRepository.getOne(id);
    }

    @Override
    public Pharmacy create(Pharmacy pharmacy) {
        if (pharmacy.getPharmacists() != null && pharmacy.getPharmacists().size() > 0) pharmacy = removeWorkingPharmacists(pharmacy);
        Pharmacy ph = pharmacy;
        if (ph.getPharmacists() != null && ph.getPharmacists().size() > 0) ph.getPharmacists().forEach(pharmacist -> pharmacist.setPharmacy(ph));
        if (ph.getDermatologists() != null && ph.getDermatologists().size() > 0) ph.getDermatologists().forEach(dermatologist -> dermatologist.getPharmacies().add(ph));
        if (ph.getConsultations() != null && ph.getConsultations().size() > 0) ph.getConsultations().forEach(consultation -> consultation.setPharmacy(ph));
        if (ph.getAdmins() != null && ph.getAdmins().size() > 0) ph.getAdmins().forEach(admin -> admin.setPharmacy(ph));
        if (ph.getExaminations() != null && ph.getExaminations().size() > 0) ph.getExaminations().forEach(examination -> examination.setPharmacy(ph));
        if (ph.getConsultations() != null && ph.getConsultations().size() > 0) ph.getConsultations().forEach(consultation -> consultation.setPharmacy(ph));
        return pharmacyRepository.save(ph);
    }

    @Override
    public Pharmacy update(Pharmacy pharmacy) {
        Pharmacy ph = removeWorkingPharmacists(pharmacy);
        return pharmacyRepository.save(ph);
    }

    public Pharmacy removeWorkingPharmacists(Pharmacy pharmacy) {
        for (Pharmacy ph : findAll()) {
            if (!ph.equals(pharmacy)) {
                // Farmaceut je zaposlen u drugoj apoteci.
                // Izbaci ga iz liste zaposlenih u ovoj apoteci.
                pharmacy.getPharmacists().removeIf(pharmacist -> ph.getPharmacists().contains(pharmacist));
            }
        }
        return pharmacy;
    }

    @Override
    public Boolean delete(Long id) {
        pharmacyRepository.deleteById(id);
        return true;
    }
}
