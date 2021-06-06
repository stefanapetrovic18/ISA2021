package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.*;
import rs.apoteka.entity.user.Dermatologist;
import rs.apoteka.entity.user.Patient;
import rs.apoteka.entity.user.Pharmacist;
import rs.apoteka.exception.InvalidRatingException;
import rs.apoteka.exception.UserNotFoundException;
import rs.apoteka.repository.business.RatingRepository;
import rs.apoteka.service.intf.auth.AuthenticationService;
import rs.apoteka.service.intf.business.MedicineService;
import rs.apoteka.service.intf.business.PharmacyService;
import rs.apoteka.service.intf.business.RatingService;
import rs.apoteka.service.intf.business.ReservationService;
import rs.apoteka.service.intf.user.DermatologistService;
import rs.apoteka.service.intf.user.PatientService;
import rs.apoteka.service.intf.user.PharmacistService;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private DermatologistService dermatologistService;
    @Autowired
    private PharmacistService pharmacistService;
    @Autowired
    private PharmacyService pharmacyService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private ReservationService reservationService;

    @Override
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    @Override
    public List<Rating> findAllParametrized(Long id, Long patientID, Long medicineID, Long dermatologistID, Long pharmacistID, Long pharmacyID) {
        List<Rating> ratings = findAll();
        if (id != null) {
            ratings.removeIf(r -> !r.getId().equals(id));
        }
        if (patientID != null) {
            ratings.removeIf(r -> r.getPatient() == null || !r.getPatient().getId().equals(patientID));
        }
        if (medicineID != null) {

            ratings.retainAll(medicineService.getOne(medicineID).getRatings());
        }
        if (dermatologistID != null) {
            ratings.retainAll(dermatologistService.getOne(dermatologistID).getRatings());
        }
        if (pharmacistID != null) {
            ratings.retainAll(pharmacistService.getOne(pharmacistID).getRatings());
        }
        if (pharmacyID != null) {
            ratings.retainAll(pharmacyService.getOne(pharmacyID).getRatings());
        }
        return ratings;
    }

    @Override
    public Double calculatePharmacyRating(Long id) {
        return calculateRating(findAllParametrized(null, null, null, null, null, id));
    }

    @Override
    public Double calculatePharmacistRating(Long id) {
        return calculateRating(findAllParametrized(null, null, null, null, id, null));
    }

    @Override
    public Double calculateDermatologistRating(Long id) {
        return calculateRating(findAllParametrized(null, null, null, id, null, null));
    }

    @Override
    public Double calculateMedicineRating(Long id) {
        return calculateRating(findAllParametrized(null, null, id, null, null, null));
    }

    private Double calculateRating(List<Rating> ratings) {
        Double sum = 0.0;
        for (Rating r :
                ratings) {
            sum += r.getRating();
        }
        return sum / ratings.size();
    }

    @Override
    public Rating getOne(Long id) {
        return ratingRepository.getOne(id);
    }

    @Override
    public Rating create(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public Rating rateMedicine(Rating rating, Long id) throws UserNotFoundException, InvalidRatingException {
        Patient patient = getPatient();
        if (patient == null) {
            throw new UserNotFoundException();
        }
        rating.setPatient(patient);
        Medicine medicine = medicineService.getOne(id);
        if (medicine == null) {
            throw new EntityNotFoundException();
        }
        if (!reservedSpecificMedicine(patient, medicine)) {
            throw new InvalidRatingException("Korisnik nije rezervisao ovaj lek.");
        }
        AtomicReference<Boolean> updated = new AtomicReference<>(false);
        medicine.getRatings().forEach(r -> {
            if (r.getPatient().getId().equals(patient.getId())) {
                r.setRating(rating.getRating());
                update(r);
                updated.set(true);
                medicine.setRating(calculatePharmacyRating(medicine.getId()));
                medicineService.update(medicine);
            }
        });
        if (updated.get()) {
            return findAllParametrized(null, patient.getId(), id, null, null, null).get(0);
        }
        Rating r = create(rating);
        if (medicine.getRatings() == null) {
            medicine.setRatings(new ArrayList<>());
        }
        medicine.getRatings().add(r);
        if (medicine.getRating() != null && medicine.getRatings() != null && medicine.getRating() > 0) {
            medicine.setRating((medicine.getRating() * (medicine.getRatings().size() - 1) + rating.getRating()) / medicine.getRatings().size());
        } else {
            medicine.setRating(Double.valueOf(rating.getRating()));
        }
        medicineService.update(medicine);
        return r;
    }

    @Override
    public Rating ratePharmacist(Rating rating, Long id) throws UserNotFoundException, InvalidRatingException {
        Patient patient = getPatient();
        if (patient == null) {
            throw new UserNotFoundException();
        }
        rating.setPatient(patient);
        Pharmacist pharmacist = pharmacistService.getOne(id);
        if (pharmacist == null) {
            throw new EntityNotFoundException();
        }
        if (!hadConsultationAtPharmacist(patient, pharmacist)) {
            throw new InvalidRatingException("Korisnik nije imao konsultaciju kod farmaceuta.");
        }
        AtomicReference<Boolean> updated = new AtomicReference<>(false);
        pharmacist.getRatings().forEach(r -> {
            if (r.getPatient().getId().equals(patient.getId())) {
                r.setRating(rating.getRating());
                update(r);
                updated.set(true);
                pharmacist.setRating(calculatePharmacyRating(pharmacist.getId()));
                pharmacistService.update(pharmacist);
            }
        });
        if (updated.get()) {
            return findAllParametrized(null, patient.getId(), null, null, id, null).get(0);
        }
        Rating r = create(rating);
        if (pharmacist.getRatings() == null) {
            pharmacist.setRatings(new ArrayList<>());
        }
        pharmacist.getRatings().add(r);
        if (pharmacist.getRating() != null && pharmacist.getRatings() != null && pharmacist.getRating() > 0) {
            pharmacist.setRating((pharmacist.getRating() * (pharmacist.getRatings().size() - 1) + rating.getRating()) / pharmacist.getRatings().size());
        } else {
            pharmacist.setRating(Double.valueOf(rating.getRating()));
        }
        pharmacistService.update(pharmacist);
        return r;
    }

    @Override
    public Rating rateDermatologist(Rating rating, Long id) throws UserNotFoundException, InvalidRatingException {
        Patient patient = getPatient();
        if (patient == null) {
            throw new UserNotFoundException();
        }
        rating.setPatient(patient);
        Dermatologist dermatologist = dermatologistService.getOne(id);
        if (dermatologist == null) {
            throw new EntityNotFoundException();
        }
        if (!hadExaminationAtDermatologist(patient, dermatologist)) {
            throw new InvalidRatingException("Korisnik nije imao pregled kod dermatologa.");
        }
        AtomicReference<Boolean> updated = new AtomicReference<>(false);
        dermatologist.getRatings().forEach(r -> {
            if (r.getPatient().getId().equals(patient.getId())) {
                r.setRating(rating.getRating());
                update(r);
                updated.set(true);
                dermatologist.setRating(calculatePharmacyRating(dermatologist.getId()));
                dermatologistService.update(dermatologist);
            }
        });
        if (updated.get()) {
            return findAllParametrized(null, patient.getId(), null, id, null, null).get(0);
        }
        Rating r = create(rating);
        if (dermatologist.getRatings() == null) {
            dermatologist.setRatings(new ArrayList<>());
        }
        dermatologist.getRatings().add(r);
        if (dermatologist.getRating() != null && dermatologist.getRatings() != null && dermatologist.getRating() > 0) {
            dermatologist.setRating((dermatologist.getRating() * (dermatologist.getRatings().size() - 1) + rating.getRating()) / dermatologist.getRatings().size());
        } else {
            dermatologist.setRating(Double.valueOf(rating.getRating()));
        }
        dermatologistService.update(dermatologist);
        return r;
    }

    @Override
    public Rating ratePharmacy(Rating rating, Long id) throws UserNotFoundException, InvalidRatingException {
        Patient patient = getPatient();
        if (patient == null) {
            throw new UserNotFoundException();
        }
        rating.setPatient(patient);
        Pharmacy pharmacy = pharmacyService.getOne(id);
        if (pharmacy == null) {
            throw new EntityNotFoundException();
        }
        if (!usedPharmacy(patient, pharmacy)) {
            throw new InvalidRatingException("Korisnik nije koristio usluge apoteke.");
        }
        AtomicReference<Boolean> updated = new AtomicReference<>(false);
        pharmacy.getRatings().forEach(r -> {
            if (r.getPatient().getId().equals(patient.getId())) {
                r.setRating(rating.getRating());
                update(r);
                updated.set(true);
                pharmacy.setRating(calculatePharmacyRating(pharmacy.getId()));
                pharmacyService.update(pharmacy);
            }
        });
        if (updated.get()) {
            return findAllParametrized(null, patient.getId(), null, null, null, id).get(0);
        }
        Rating r = create(rating);
        if (pharmacy.getRatings() == null) {
            pharmacy.setRatings(new ArrayList<>());
        }
        pharmacy.getRatings().add(r);

        System.out.println(pharmacy.getRating());
        if (pharmacy.getRating() != null && pharmacy.getRatings() != null && pharmacy.getRating() > 0) {
            pharmacy.setRating((pharmacy.getRating() * (pharmacy.getRatings().size() - 1) + rating.getRating()) / pharmacy.getRatings().size());
        } else {
            pharmacy.setRating(Double.valueOf(rating.getRating()));
        }
        System.out.println(pharmacy.getRating());

        pharmacyService.update(pharmacy);
        return r;
    }

    private Patient getPatient() {
        return patientService.findByUsername(authenticationService.getUsername());
    }

    @Override
    // Proverava da li je pacijent rezervisao i preuzeo lek.
    public Boolean reservedSpecificMedicine(Patient patient, Medicine medicine) {
        for (Reservation res : reservationService.findAllParametrized(null, null, null,
                null, null, medicine.getId(),
                patient.getId(), true, null, null)) {
            if (res.getCollectionDate().isBefore(LocalDateTime.now())) {
                return true;
            }
        }
        return false;
    }

    @Override
    // Proverava da li je pacijent imao pregled kod dermatologa.
    public Boolean hadExaminationAtDermatologist(Patient patient, Dermatologist dermatologist) {
        for (Examination exam : patient.getExaminations()) {
            if (exam.getDermatologist().getId().equals(dermatologist.getId()) && exam.getExaminationDate().isBefore(LocalDateTime.now())) {
                return true;
            }
        }
        return false;
    }

    @Override
    // Proverava da li je pacijent imao konsultacije kod farmaceuta.
    public Boolean hadConsultationAtPharmacist(Patient patient, Pharmacist pharmacist) {
        for (Consultation cons : patient.getConsultations()) {
            if (cons.getPharmacist().getId().equals(pharmacist.getId()) && cons.getConsultationDate().isBefore(LocalDateTime.now())) {
                return true;
            }
        }
        return false;
    }

    @Override
    // Proverava da li je pacijent koristio usluge apoteke.
    public Boolean usedPharmacy(Patient patient, Pharmacy pharmacy) {
        // Ukoliko je bio na konsultaciji u ovoj apoteci, uslov je zadovoljen.
        for (Consultation c : patient.getConsultations()) {
            if (c.getPharmacy().getId().equals(pharmacy.getId()) && c.getConsultationDate().isBefore(LocalDateTime.now())) {
                return true;
            }
        }
        // Ukoliko je bio na pregledu u ovoj apoteci, uslov je zadovoljen.
        for (Examination e : patient.getExaminations()) {
            if (e.getPharmacy().getId().equals(pharmacy.getId()) && e.getExaminationDate().isBefore(LocalDateTime.now())) {
                return true;
            }
        }
        // Ukoliko je rezervisao i preuzeo lek u ovoj apoteci, uslov je zadovoljen.
        for (Reservation r : reservationService.findAllParametrized(null, null, null,
                null, null, null,
                patient.getId(), true, null, null)) {
            if (r.getPharmacy().getId().equals(pharmacy.getId()) && r.getCollectionDate().isBefore(LocalDateTime.now())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Rating update(Rating rating) {
        Rating r = getOne(rating.getId());
        r.setRating(rating.getRating());
        return ratingRepository.save(r);
    }

    @Override
    public Boolean delete(Long id) {
        Rating rating = getOne(id);
        if (rating == null) {
            return false;
        }
        pharmacyService.findAll().forEach(p -> {
            p.getRatings().removeIf(r -> r.getId().equals(id));
            pharmacyService.update(p);
        });
        dermatologistService.findAll().forEach(d -> {
            d.getRatings().removeIf(r -> r.getId().equals(id));
            dermatologistService.update(d);
        });
        pharmacistService.findAll().forEach(p -> {
            p.getRatings().removeIf(r -> r.getId().equals(id));
            pharmacistService.update(p);
        });
        medicineService.findAll().forEach(m -> {
            m.getRatings().removeIf(r -> r.getId().equals(id));
            medicineService.update(m);
        });
        ratingRepository.deleteById(id);
        return getOne(id) == null;
    }
}
