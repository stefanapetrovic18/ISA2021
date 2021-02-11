package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Consultation;
import rs.apoteka.entity.business.Pharmacy;
import rs.apoteka.entity.user.Pharmacist;
import rs.apoteka.repository.business.PharmacyRepository;
import rs.apoteka.service.intf.business.PharmacyService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Pharmacy> findAllParametrized(Long id, Long pharmacistID, Long dermatologistID, Long consultationID,
                                              Long adminID, Long examinationID, Long promotionID, Long userID,
                                              Long pricelistID, Double rating, Double ratingFrom, Double ratingTo) {
        List<Pharmacy> pharmacies = findAll();
        if (id != null) {
            pharmacies.removeIf(p -> !p.getId().equals(id));
        }
        if (pharmacistID != null) {
            pharmacies = pharmacies.stream().filter(p -> p.getPharmacists().removeIf(ph -> !ph.getId().equals(pharmacistID))).collect(Collectors.toList());
        }
        if (dermatologistID != null) {
            pharmacies = pharmacies.stream().filter(p -> p.getDermatologists().removeIf(ph -> !ph.getId().equals(dermatologistID))).collect(Collectors.toList());
        }
        if (consultationID != null) {
            pharmacies = pharmacies.stream().filter(p -> p.getConsultations().removeIf(ph -> !ph.getId().equals(consultationID))).collect(Collectors.toList());
        }
        if (adminID != null) {
            pharmacies = pharmacies.stream().filter(p -> p.getAdmins().removeIf(ph -> !ph.getId().equals(adminID))).collect(Collectors.toList());
        }
        if (examinationID != null) {
            pharmacies = pharmacies.stream().filter(p -> p.getExaminations().removeIf(ph -> !ph.getId().equals(examinationID))).collect(Collectors.toList());
        }
        if (promotionID != null) {
            pharmacies = pharmacies.stream().filter(p -> p.getPromotions().removeIf(ph -> !ph.getId().equals(promotionID))).collect(Collectors.toList());
        }
        if (userID != null) {
            pharmacies = pharmacies.stream().filter(p -> p.getSubscriptions().removeIf(ph -> !ph.getId().equals(userID))).collect(Collectors.toList());
        }
        if (pricelistID != null) {
            pharmacies.removeIf(p -> !p.getPricelist().getId().equals(pricelistID));
        }
        if (rating != null) {
            pharmacies.removeIf(p -> !p.getRating().equals(rating));
        }
        if (ratingFrom != null) {
            pharmacies.removeIf(p -> p.getRating() < ratingFrom);
        }
        if (ratingTo != null) {
            pharmacies.removeIf(p -> p.getRating() > ratingTo);
        }
        return pharmacies;
    }

    @Override
    public List<Pharmacy> findAllByPharmacistFreeAt(LocalDateTime localDateTime) {
        List<Pharmacy> pharmacies = new ArrayList<>();
        for (Pharmacy p : findAll()) {
            for (Pharmacist ph : p.getPharmacists()) {
                for (Consultation c : ph.getConsultations()) {
                    if (c.getConsultationDate().plusMinutes(c.getDuration()).isBefore(localDateTime)
                            ||
                            c.getConsultationDate().isAfter(localDateTime)) {
                        pharmacies.add(p);
                    }
                }
            }
        }
        return pharmacies;
    }

    @Override
    public Pharmacy create(Pharmacy pharmacy) {
        if (pharmacy.getPharmacists() != null && pharmacy.getPharmacists().size() > 0)
            pharmacy = removeWorkingPharmacists(pharmacy);
        Pharmacy ph = pharmacy;
        if (ph.getPharmacists() != null && ph.getPharmacists().size() > 0)
            ph.getPharmacists().forEach(pharmacist -> pharmacist.setPharmacy(ph));
        if (ph.getDermatologists() != null && ph.getDermatologists().size() > 0)
            ph.getDermatologists().forEach(dermatologist -> dermatologist.getPharmacies().add(ph));
        if (ph.getConsultations() != null && ph.getConsultations().size() > 0)
            ph.getConsultations().forEach(consultation -> consultation.setPharmacy(ph));
        if (ph.getAdmins() != null && ph.getAdmins().size() > 0) ph.getAdmins().forEach(admin -> admin.setPharmacy(ph));
        if (ph.getExaminations() != null && ph.getExaminations().size() > 0)
            ph.getExaminations().forEach(examination -> examination.setPharmacy(ph));
        if (ph.getPromotions() != null && ph.getPromotions().size() > 0)
            ph.getPromotions().forEach(consultation -> consultation.setPharmacy(ph));
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
