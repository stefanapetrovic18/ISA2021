package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Examination;

import java.util.List;

public interface ExaminationService {
    List<Examination> findAll();

    Examination getOne(Long id);

    Examination create(Examination examination);

    Examination update(Examination examination);

    Boolean delete(Long id);
}
