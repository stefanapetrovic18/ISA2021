package rs.apoteka.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.business.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
}
