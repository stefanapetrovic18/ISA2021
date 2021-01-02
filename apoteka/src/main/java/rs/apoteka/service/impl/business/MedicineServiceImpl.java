package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Medicine;
import rs.apoteka.repository.business.MedicineRepository;
import rs.apoteka.service.intf.business.MedicineService;

import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {
    @Autowired
    private MedicineRepository medicineRepository;

    @Override
    public List<Medicine> findAll() {
        return medicineRepository.findAll();
    }

    @Override
    public Medicine getOne(Long id) {
        return medicineRepository.getOne(id);
    }

    @Override
    public Medicine create(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    @Override
    public Medicine update(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    @Override
    public Boolean delete(Long id) {
        medicineRepository.deleteById(id);
        return true;
    }
}
