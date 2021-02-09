package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.WorkingHours;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface WorkingHoursService {
    List<WorkingHours> findAll();

    List<WorkingHours> findAllByEmployeeID(Long id);

    List<WorkingHours> findAllByPharmacyId(Long id);

    List<WorkingHours> findAllParametrized(Long id, Long employeeID, Long pharmacyID, LocalTime shiftStart, LocalTime shiftEnd, DayOfWeek dayOfWeek);

    WorkingHours getOne(Long id);

    WorkingHours create(WorkingHours workingHours);

    WorkingHours update(WorkingHours workingHours);

    Boolean delete(Long id);
}
