package rs.apoteka.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.user.Pharmacist;
import rs.apoteka.repository.user.PharmacistRepository;
import rs.apoteka.service.intf.user.PharmacistService;

import java.util.List;

@Service
public class PharmacistServiceImpl implements PharmacistService {
    @Autowired
    private PharmacistRepository pharmacistRepository;

    @Override
    public List<Pharmacist> findAll() {
        return pharmacistRepository.findAll();
    }

    @Override
    public Pharmacist getOne(Long id) {
        return pharmacistRepository.getOne(id);
    }

    @Override
    public Pharmacist create(Pharmacist pharmacist) {
        return pharmacistRepository.save(pharmacist);
    }

    @Override
    public Pharmacist update(Pharmacist pharmacist) {
        return pharmacistRepository.save(pharmacist);
    }

    @Override
    public Boolean delete(Long id) {
        pharmacistRepository.deleteById(id);
        return true;
    }
}
