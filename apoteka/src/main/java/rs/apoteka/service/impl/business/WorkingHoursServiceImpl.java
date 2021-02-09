package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.auth.User;
import rs.apoteka.entity.business.WorkingHours;
import rs.apoteka.entity.user.Dermatologist;
import rs.apoteka.entity.user.Pharmacist;
import rs.apoteka.repository.business.WorkingHoursRepository;
import rs.apoteka.service.intf.auth.UserService;
import rs.apoteka.service.intf.business.WorkingHoursService;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Service
public class WorkingHoursServiceImpl implements WorkingHoursService {
    @Autowired
    private WorkingHoursRepository workingHoursRepository;
    @Autowired
    private UserService userService;

    @Override
    public List<WorkingHours> findAll() {
        return workingHoursRepository.findAll();
    }

    @Override
    public List<WorkingHours> findAllByEmployeeID(Long id) {
        return workingHoursRepository.findAllByEmployeeID(id);
    }

    @Override
    public List<WorkingHours> findAllByPharmacyId(Long id) {
        return workingHoursRepository.findAllByPharmacy_Id(id);
    }

    @Override
    public List<WorkingHours> findAllParametrized(Long id, Long employeeID, Long pharmacyID, LocalTime shiftStart, LocalTime shiftEnd, DayOfWeek dayOfWeek) {
        List<WorkingHours> workingHours = findAll();
        if (id != null) {
            workingHours.removeIf(wh -> !wh.getId().equals(id));
        }
        if (employeeID != null) {
            workingHours.removeIf(wh -> !wh.getEmployeeID().equals(employeeID));
        }
        if (pharmacyID != null) {
            workingHours.removeIf(wh -> !wh.getPharmacy().getId().equals(pharmacyID));
        }
        if (shiftStart != null) {
            workingHours.removeIf(wh -> !wh.getShiftStart().isBefore(shiftStart));
        }
        if (shiftEnd != null) {
            workingHours.removeIf(wh -> !wh.getShiftEnd().isAfter(shiftEnd));
        }
        if (dayOfWeek != null) {
            workingHours.removeIf(wh -> !wh.getDayOfWeek().equals(dayOfWeek));
        }
        return workingHours;
    }

    @Override
    public WorkingHours getOne(Long id) {
        return workingHoursRepository.getOne(id);
    }

    @Override
    public WorkingHours create(WorkingHours workingHours) {
        if (!checkTimes(workingHours)) {
            return null;
        }
        return workingHoursRepository.save(workingHours);
    }

    @Override
    public WorkingHours update(WorkingHours workingHours) {
        if (!checkTimes(workingHours)) {
            return null;
        }
        return workingHoursRepository.save(workingHours);
    }

    private Boolean checkTimes(WorkingHours workingHours) {
        return checkHours(workingHours) && checkFree(workingHours);
    }

    private Boolean checkHours(WorkingHours workingHours) {
        return workingHours.getShiftStart().isBefore(workingHours.getShiftEnd());
    }

    private Boolean checkFree(WorkingHours workingHours) {
        List<WorkingHours> hours = getEmployeeWorkingHours(workingHours);
        if (hours == null) {
            return false;
        }
        Boolean flag = false;
        for (WorkingHours wh : hours) {
            if (wh.getDayOfWeek() == workingHours.getDayOfWeek()) {
                if (wh.getShiftStart().isAfter(workingHours.getShiftEnd()) ||
                wh.getShiftEnd().isBefore(workingHours.getShiftStart())) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    private List<WorkingHours> getEmployeeWorkingHours(WorkingHours workingHours) {
        User user = userService.getOne(workingHours.getEmployeeID());
        if (user instanceof Dermatologist) {
            return ((Dermatologist) user).getWorkingHours();
        } else if (user instanceof Pharmacist) {
            return ((Pharmacist) user).getWorkingHours();
        } else {
            return null;
        }
    }

    @Override
    public Boolean delete(Long id) {
        workingHoursRepository.deleteById(id);
        return true;
    }
}
