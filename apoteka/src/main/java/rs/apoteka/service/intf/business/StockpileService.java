package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Stockpile;
import rs.apoteka.exception.DataMismatchException;
import rs.apoteka.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface StockpileService {
    List<Stockpile> findAll();

    List<Stockpile> findAllParametrized(Long id, Long medicineID, Long pharmacyID, Integer quantity, Integer quantityFrom, Integer quantityTo);

    Stockpile getOne(Long id);

    Stockpile create(Stockpile stockpile) throws UserNotFoundException, DataMismatchException;

    Stockpile update(Stockpile stockpile) throws UserNotFoundException, DataMismatchException;

    Boolean delete(Long id);
}
