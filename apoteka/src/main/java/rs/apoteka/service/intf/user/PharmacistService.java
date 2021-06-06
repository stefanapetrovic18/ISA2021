package rs.apoteka.service.intf.user;

import rs.apoteka.entity.user.Pharmacist;
import rs.apoteka.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface PharmacistService {
    List<Pharmacist> findAll();

    List<Pharmacist> findAllUnemployed();

    List<Pharmacist> findAllByPharmacistFreeAt(Long pharmacyID, LocalDateTime localDateTime);

    Pharmacist findByUsername(String username);

    List<Pharmacist> findAllParametrized(Long id, Long workingHoursID, Long pharmacyID, LocalDateTime vacationStart, LocalDateTime vacationEnd,
                                         Long consultationID, Double rating, Double ratingFrom, Double ratingTo);

    Pharmacist getOne(Long id);

    Pharmacist create(Pharmacist pharmacist) throws Exception;

    Pharmacist fire(Pharmacist pharmacist) throws UserNotFoundException;

    Pharmacist update(Pharmacist pharmacist);

    Boolean delete(Long id);
}
