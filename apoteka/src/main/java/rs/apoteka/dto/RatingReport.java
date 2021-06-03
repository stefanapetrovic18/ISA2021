package rs.apoteka.dto;

import java.util.List;

public class RatingReport {
    private Double pharmacyRating;
    private List<PharmacistRating> pharmacistRatings;
    private List<DermatologistRating> dermatologistRatings;

    public RatingReport() {
    }

    public Double getPharmacyRating() {
        return pharmacyRating;
    }

    public void setPharmacyRating(Double pharmacyRating) {
        this.pharmacyRating = pharmacyRating;
    }

    public List<PharmacistRating> getPharmacistRatings() {
        return pharmacistRatings;
    }

    public void setPharmacistRatings(List<PharmacistRating> pharmacistRatings) {
        this.pharmacistRatings = pharmacistRatings;
    }

    public List<DermatologistRating> getDermatologistRatings() {
        return dermatologistRatings;
    }

    public void setDermatologistRatings(List<DermatologistRating> dermatologistRatings) {
        this.dermatologistRatings = dermatologistRatings;
    }
}
