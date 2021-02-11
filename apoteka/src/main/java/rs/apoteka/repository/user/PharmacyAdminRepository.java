package rs.apoteka.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.user.PharmacyAdmin;

public interface PharmacyAdminRepository extends JpaRepository<PharmacyAdmin, Long> {
    PharmacyAdmin findByUsername(String username);
}
