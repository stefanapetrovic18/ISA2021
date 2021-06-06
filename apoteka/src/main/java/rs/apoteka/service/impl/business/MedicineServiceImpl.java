package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Medicine;
import rs.apoteka.entity.business.Pharmacy;
import rs.apoteka.repository.business.MedicineRepository;
import rs.apoteka.service.intf.business.MedicineService;
import rs.apoteka.service.intf.business.PharmacyService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {
    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired
    private PharmacyService pharmacyService;

    @Override
    public List<Medicine> findAll() {
        return medicineRepository.findAll();
    }

    @Override
    public List<Medicine> findAllParametrized(Long id, String name, String code, String manufacturer, String type, String form,
                                              Boolean prescriptionNecessary, String sideEffects, String ingredient,
                                              String recommendedDose) {
        List<Medicine> medicine = findAll();
        if (id != null) {
            medicine.removeIf(m -> !m.getId().equals(id));
        }
        if (name != null) {
            medicine.removeIf(m -> !m.getName().contains(name));
        }
        if (code != null) {
            medicine.removeIf(m -> !m.getCode().contains(code));
        }
        if (manufacturer != null) {
            medicine.removeIf(m -> !m.getManufacturer().contains(manufacturer));
        }
        if (type != null) {
            medicine.removeIf(m -> !m.getType().equals(type));
        }
        if (form != null) {
            medicine.removeIf(m -> !m.getForm().contains(form));
        }
        if (prescriptionNecessary != null) {
            medicine.removeIf(m -> m.getPrescriptionNecessary() != prescriptionNecessary);
        }
        if (sideEffects != null) {
            medicine.removeIf(m -> !m.getSideEffects().contains(sideEffects));
        }
        if (ingredient != null) {
            medicine.removeIf(m -> !m.getIngredients().contains(ingredient));
        }
        if (recommendedDose != null) {
            medicine.removeIf(m -> !m.getRecommendedDose().contains(recommendedDose));
        }
        return medicine;
    }

    @Override
    public List<Medicine> findByPharmacy(Long pharmacyID) {
        Pharmacy pharmacy = pharmacyService.getOne(pharmacyID);
        if (pharmacy == null) {
            throw new EntityNotFoundException();
        }
        List<Medicine> medicine = new ArrayList<>();
        pharmacy.getStockpile().forEach(s -> {
            if (s.getQuantity() > 0) {
                medicine.add(s.getMedicine());
            }
        });
        return medicine;
    }

    @Override
    public List<Medicine> findSubstitutes(Long id) {
        return getOne(id).getSubstitutes();
    }

    @Override
    public Medicine getOne(Long id) {
        return medicineRepository.getOne(id);
    }

    @Override
    public Medicine create(Medicine medicine) {
        if (medicine.getSubstitutes() != null && medicine.getSubstitutes().contains(medicine)) {
            // Sadrzi sebe u listi zamenskih lekova.
            return null;
        }
        return medicineRepository.save(medicine);
    }

    @Override
    public Medicine update(Medicine medicine) {
        if (medicine.getSubstitutes().contains(medicine)) {
            // Sadrzi sebe u listi zamenskih lekova.
            return null;
        }
        return medicineRepository.save(medicine);
    }

    @Override
    public Boolean delete(Long id) {
        medicineRepository.deleteById(id);
        return true;
    }
}
