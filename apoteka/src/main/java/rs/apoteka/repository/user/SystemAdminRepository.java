package rs.apoteka.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.user.SystemAdmin;

public interface SystemAdminRepository extends JpaRepository<SystemAdmin, Long> {
}
