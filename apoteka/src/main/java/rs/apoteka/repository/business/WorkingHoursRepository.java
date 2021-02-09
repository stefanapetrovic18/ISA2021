package rs.apoteka.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.business.WorkingHours;

import java.util.List;

public interface WorkingHoursRepository extends JpaRepository<WorkingHours, Long> {
    List<WorkingHours> findAllByEmployeeID(Long employeeID);

    List<WorkingHours> findAllByPharmacy_Id(Long id);
}
