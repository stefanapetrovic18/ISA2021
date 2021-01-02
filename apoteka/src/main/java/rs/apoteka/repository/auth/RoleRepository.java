package rs.apoteka.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.auth.Role;
import rs.apoteka.entity.auth.RoleType;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleType name);
}
