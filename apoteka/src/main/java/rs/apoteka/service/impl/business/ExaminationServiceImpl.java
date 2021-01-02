package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Examination;
import rs.apoteka.repository.business.ExaminationRepository;
import rs.apoteka.service.intf.business.ExaminationService;

import java.util.List;

@Service
public class ExaminationServiceImpl implements ExaminationService {
    @Autowired
    private ExaminationRepository examinationRepository;

    @Override
    public List<Examination> findAll() {
        return examinationRepository.findAll();
    }

    @Override
    public Examination getOne(Long id) {
        return examinationRepository.getOne(id);
    }

    @Override
    public Examination create(Examination examination) {
        return examinationRepository.save(examination);
    }

    @Override
    public Examination update(Examination examination) {
        return examinationRepository.save(examination);
    }

    @Override
    public Boolean delete(Long id) {
        examinationRepository.deleteById(id);
        return true;
    }
}
