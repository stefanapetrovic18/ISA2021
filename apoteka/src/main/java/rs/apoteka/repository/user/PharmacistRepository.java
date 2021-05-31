package rs.apoteka.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.user.Pharmacist;

public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {
    Pharmacist findByUsername(String username);
}
