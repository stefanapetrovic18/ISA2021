package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Consultation;
import rs.apoteka.repository.business.ConsultationRepository;
import rs.apoteka.service.intf.business.ConsultationService;

import java.util.List;

@Service
public class ConsultationServiceImpl implements ConsultationService {
    @Autowired
    private ConsultationRepository consultationRepository;

    @Override
    public List<Consultation> findAll() {
        return consultationRepository.findAll();
    }

    @Override
    public Consultation getOne(Long id) {
        return consultationRepository.getOne(id);
    }

    @Override
    public Consultation create(Consultation consultation) {
        return consultationRepository.save(consultation);
    }

    @Override
    public Consultation update(Consultation consultation) {
        return consultationRepository.save(consultation);
    }

    @Override
    public Boolean delete(Long id) {
        consultationRepository.deleteById(id);
        return true;
    }
}
