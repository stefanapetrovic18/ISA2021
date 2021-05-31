package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Medicine;
import rs.apoteka.entity.business.Pharmacy;
import rs.apoteka.entity.business.Rating;
import rs.apoteka.entity.user.Dermatologist;
import rs.apoteka.entity.user.Patient;
import rs.apoteka.entity.user.Pharmacist;
import rs.apoteka.exception.AuthMismatchException;
import rs.apoteka.exception.InvalidRatingException;
import rs.apoteka.exception.UserNotFoundException;

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

    Rating rateMedicine(Rating rating, Long id) throws UserNotFoundException, AuthMismatchException, InvalidRatingException;

    Rating ratePharmacist(Rating rating, Long id) throws UserNotFoundException, AuthMismatchException, InvalidRatingException;

    Rating rateDermatologist(Rating rating, Long id) throws UserNotFoundException, AuthMismatchException, InvalidRatingException;

    Rating ratePharmacy(Rating rating, Long id) throws UserNotFoundException, AuthMismatchException, InvalidRatingException;

    // Proverava da li je pacijent rezervisao i preuzeo lek.
    Boolean reservedSpecificMedicine(Patient patient, Medicine medicine);

    // Proverava da li je pacijent imao pregled kod dermatologa.
    Boolean hadExaminationAtDermatologist(Patient patient, Dermatologist dermatologist);

    // Proverava da li je pacijent imao konsultacije kod farmaceuta.
    Boolean hadConsultationAtPharmacist(Patient patient, Pharmacist pharmacist);

    // Proverava da li je pacijent koristio usluge apoteke.
    Boolean usedPharmacy(Patient patient, Pharmacy pharmacy);

    Rating update(Rating rating);

    Boolean delete(Long id);
}
