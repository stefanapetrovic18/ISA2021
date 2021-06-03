package rs.apoteka.dto;

public class DermatologistRating {
    private String dermatologistName;
    private Double dermatologistRating;

    public DermatologistRating() {
    }

    public String getDermatologistName() {
        return dermatologistName;
    }

    public void setDermatologistName(String dermatologistName) {
        this.dermatologistName = dermatologistName;
    }

    public Double getDermatologistRating() {
        return dermatologistRating;
    }

    public void setDermatologistRating(Double dermatologistRating) {
        this.dermatologistRating = dermatologistRating;
    }
}
