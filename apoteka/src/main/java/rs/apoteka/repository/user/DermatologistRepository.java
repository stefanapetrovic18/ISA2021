package rs.apoteka.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.business.Pharmacy;
import rs.apoteka.entity.user.Dermatologist;

import java.util.List;

public interface DermatologistRepository extends JpaRepository<Dermatologist, Long> {
    List<Dermatologist> findAllByPharmaciesContaining(Pharmacy pharmacy);
}
