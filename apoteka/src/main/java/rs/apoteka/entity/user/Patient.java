package rs.apoteka.entity.user;

import rs.apoteka.entity.auth.User;
import rs.apoteka.entity.business.Consultation;
import rs.apoteka.entity.business.Examination;
import rs.apoteka.entity.business.Medicine;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Patient extends User {
    @Column(nullable = false)
    private Integer points;
    @OneToMany(mappedBy = "patient")
    private List<Examination> examinations;
    @OneToMany(mappedBy = "patient")
    private List<Consultation> consultations;
    @ManyToMany
    private List<Medicine> allergies;

    public Patient() {
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public List<Examination> getExaminations() {
        return examinations;
    }

    public void setExaminations(List<Examination> examinations) {
        this.examinations = examinations;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

    public List<Medicine> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<Medicine> allergies) {
        this.allergies = allergies;
    }
}
