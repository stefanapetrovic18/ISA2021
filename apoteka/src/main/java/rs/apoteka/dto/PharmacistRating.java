package rs.apoteka.dto;

public class PharmacistRating {
    private String pharmacistName;
    private Double pharmacistRating;

    public PharmacistRating() {
    }

    public String getPharmacistName() {
        return pharmacistName;
    }

    public void setPharmacistName(String pharmacistName) {
        this.pharmacistName = pharmacistName;
    }

    public Double getPharmacistRating() {
        return pharmacistRating;
    }

    public void setPharmacistRating(Double pharmacistRating) {
        this.pharmacistRating = pharmacistRating;
    }
}
