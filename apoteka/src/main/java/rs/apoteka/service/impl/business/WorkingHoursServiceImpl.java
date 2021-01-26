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
