package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Consultation;

import java.util.List;

public interface ConsultationService {
    List<Consultation> findAll();

    Consultation getOne(Long id);

    Consultation create(Consultation consultation);

    Consultation update(Consultation consultation);

    Boolean delete(Long id);
}
