package rs.apoteka.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.business.Rating;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findAllByPatient_Id(Long patientID);
}
