package rs.apoteka.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.user.Dermatologist;
import rs.apoteka.repository.user.DermatologistRepository;
import rs.apoteka.service.intf.user.DermatologistService;

import java.util.List;

@Service
public class DermatologistServiceImpl implements DermatologistService {
    @Autowired
    private DermatologistRepository dermatologistRepository;

    @Override
    public List<Dermatologist> findAll() {
        return dermatologistRepository.findAll();
    }

    @Override
    public Dermatologist getOne(Long id) {
        return dermatologistRepository.getOne(id);
    }

    @Override
    public Dermatologist create(Dermatologist dermatologist) {
        return dermatologistRepository.save(dermatologist);
    }

    @Override
    public Dermatologist update(Dermatologist dermatologist) {
        return dermatologistRepository.save(dermatologist);
    }

    @Override
    public Boolean delete(Long id) {
        dermatologistRepository.deleteById(id);
        return true;
    }
}
