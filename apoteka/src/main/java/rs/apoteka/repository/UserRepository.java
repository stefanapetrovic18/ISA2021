package rs.apoteka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.auth.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByValidatedTrueAndEnabledTrue();
    User findByUsername(String username);
    Boolean existsByUsername(String username);
}
