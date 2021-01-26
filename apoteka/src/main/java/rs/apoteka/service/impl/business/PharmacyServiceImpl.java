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
        Pharmacy ph = removeWorkingPharmacists(pharmacy);
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
