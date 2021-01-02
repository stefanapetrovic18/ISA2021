package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Medicine;

import java.util.List;

public interface MedicineService {
    List<Medicine> findAll();

    Medicine getOne(Long id);

    Medicine create(Medicine medicine);

    Medicine update(Medicine Medicine);

    Boolean delete(Long id);
}
