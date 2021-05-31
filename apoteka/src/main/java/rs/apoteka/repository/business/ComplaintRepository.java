package rs.apoteka.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.business.Complaint;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findAllByPatient_Id(Long patientID);
}
