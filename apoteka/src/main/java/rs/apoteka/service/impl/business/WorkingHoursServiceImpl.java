package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.WorkingHours;
import rs.apoteka.repository.business.WorkingHoursRepository;
import rs.apoteka.service.intf.business.WorkingHoursService;

import java.util.List;

@Service
public class WorkingHoursServiceImpl implements WorkingHoursService {
    @Autowired
    private WorkingHoursRepository workingHoursRepository;

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
        return workingHoursRepository.save(workingHours);
    }

    @Override
    public WorkingHours update(WorkingHours workingHours) {
        return workingHoursRepository.save(workingHours);
    }

    @Override
    public Boolean delete(Long id) {
        workingHoursRepository.deleteById(id);
        return true;
    }
}
