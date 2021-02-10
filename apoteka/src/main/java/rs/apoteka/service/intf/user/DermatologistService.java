package rs.apoteka.service.intf.user;

import rs.apoteka.entity.business.Pharmacy;
import rs.apoteka.entity.user.Dermatologist;

import java.time.LocalDateTime;
import java.util.List;

public interface DermatologistService {
    List<Dermatologist> findAll();

    List<Dermatologist> findAllByPharmaciesContaining(Pharmacy pharmacy);

    List<Dermatologist> findAllNotEmployedInPharmacy(Long pharmacyID);

    List<Dermatologist> findAllParametrized(Long id, Long workingHoursID, Long pharmacyID, LocalDateTime vacationStart, LocalDateTime vacationEnd,
                                            Long examinationID, Double rating, Double ratingFrom, Double ratingTo);

    Dermatologist getOne(Long id);

    Dermatologist create(Dermatologist dermatologist);

    Dermatologist update(Dermatologist dermatologist);

    Boolean delete(Long id);
}
