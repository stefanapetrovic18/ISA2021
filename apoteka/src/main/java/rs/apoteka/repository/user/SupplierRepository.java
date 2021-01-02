package rs.apoteka.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.user.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
