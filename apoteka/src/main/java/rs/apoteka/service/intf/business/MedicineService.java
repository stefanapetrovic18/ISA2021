package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Medicine;

import java.util.List;

public interface MedicineService {
    List<Medicine> findAll();

    List<Medicine> findAllParametrized(Long id, String name, String code, String manufacturer, String type, String form,
                                       Boolean prescriptionNecessary, String sideEffects, String ingredient,
                                       String recommendedDose);

    List<Medicine> findSubstitutes(Long id);

    Medicine getOne(Long id);

    Medicine create(Medicine medicine);

    Medicine update(Medicine Medicine);

    Boolean delete(Long id);
}
