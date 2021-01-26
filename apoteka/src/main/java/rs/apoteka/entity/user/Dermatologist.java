package rs.apoteka.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import rs.apoteka.entity.auth.User;
import rs.apoteka.entity.business.Examination;
import rs.apoteka.entity.business.Pharmacy;
import rs.apoteka.entity.business.WorkingHours;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Dermatologist extends User {
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime vacationStart;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime vacationEnd;
    @ManyToMany
    private List<Pharmacy> pharmacies;
    @OneToMany(mappedBy = "dermatologist", cascade = CascadeType.ALL)
    private List<Examination> appointments;
    @Column
    @ElementCollection(targetClass = WorkingHours.class)
    private List<WorkingHours> workingHours;
    @Column
    private Double rating;

    public Dermatologist() {
    }

    public LocalDateTime getVacationStart() {
        return vacationStart;
    }

    public void setVacationStart(LocalDateTime vacationStart) {
        this.vacationStart = vacationStart;
    }

    public LocalDateTime getVacationEnd() {
        return vacationEnd;
    }

    public void setVacationEnd(LocalDateTime vacationEnd) {
        this.vacationEnd = vacationEnd;
    }

    public List<Pharmacy> getPharmacies() {
        return pharmacies;
    }

    public void setPharmacies(List<Pharmacy> pharmacies) {
        this.pharmacies = pharmacies;
    }

    public List<Examination> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Examination> appointments) {
        this.appointments = appointments;
    }

    public List<WorkingHours> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(List<WorkingHours> workingHours) {
        this.workingHours = workingHours;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
