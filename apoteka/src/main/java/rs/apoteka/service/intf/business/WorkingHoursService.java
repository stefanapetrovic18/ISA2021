package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.WorkingHours;

import java.util.List;

public interface WorkingHoursService {
    List<WorkingHours> findAll();

    WorkingHours getOne(Long id);

    WorkingHours create(WorkingHours workingHours);

    WorkingHours update(WorkingHours workingHours);

    Boolean delete(Long id);
}
