package rs.apoteka.entity.user;

import rs.apoteka.entity.auth.User;
import rs.apoteka.entity.business.Examination;
import rs.apoteka.entity.business.Pharmacy;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Dermatologist extends User {
    @ManyToMany
    private List<Pharmacy> pharmacies;
    @OneToMany(mappedBy = "dermatologist", cascade = CascadeType.ALL)
    private List<Examination> appointments;

    public Dermatologist() {
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
}
