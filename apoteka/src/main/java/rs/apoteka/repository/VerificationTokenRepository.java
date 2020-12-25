package rs.apoteka.repository;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.auth.User;
import rs.apoteka.entity.auth.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByUser(User user);
    VerificationToken findByToken(String token);
}
