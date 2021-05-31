package rs.apoteka.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.apoteka.entity.business.Stockpile;

import java.util.List;

public interface StockpileRepository extends JpaRepository<Stockpile, Long> {
    List<Stockpile> findAllByPharmacy_Id(Long pharmacyID);

    List<Stockpile> findAllByMedicine_Id(Long medicineID);
}
