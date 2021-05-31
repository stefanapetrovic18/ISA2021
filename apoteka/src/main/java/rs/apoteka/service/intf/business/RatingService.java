package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Rating;
import rs.apoteka.exception.AuthMismatchException;
import rs.apoteka.exception.InvalidRatingException;
import rs.apoteka.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface RatingService {
    List<Rating> findAll();

    List<Rating> findAllParametrized(Long id, Long patientID, Long medicineID, Long dermatologistID, Long pharmacistID, Long pharmacyID);

    Double calculatePharmacyRating(Long id);
    Double calculatePharmacistRating(Long id);
    Double calculateDermatologistRating(Long id);
    Double calculateMedicineRating(Long id);

    Rating getOne(Long id);

    Rating create(Rating rating);

    public Rating rateMedicine(Rating rating, Long id) throws UserNotFoundException, AuthMismatchException, InvalidRatingException;
    public Rating ratePharmacist(Rating rating, Long id) throws UserNotFoundException, AuthMismatchException, InvalidRatingException;
    public Rating rateDermatologist(Rating rating, Long id) throws UserNotFoundException, AuthMismatchException, InvalidRatingException;
    public Rating ratePharmacy(Rating rating, Long id) throws UserNotFoundException, AuthMismatchException, InvalidRatingException;

    Rating update(Rating rating);

    Boolean delete(Long id);
}
