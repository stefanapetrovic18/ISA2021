package rs.apoteka.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.user.Dermatologist;

public interface DermatologistRepository extends JpaRepository<Dermatologist, Long> {
}
