package rs.apoteka.entity.user;

import rs.apoteka.entity.auth.User;
import rs.apoteka.entity.business.Consultation;
import rs.apoteka.entity.business.Examination;
import rs.apoteka.entity.business.Pharmacy;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Pharmacist extends User {
    @ManyToOne
    @JoinColumn(name = "pharmacy", referencedColumnName = "id")
    private Pharmacy pharmacy;
    @OneToMany(mappedBy = "pharmacist", cascade = CascadeType.ALL)
    private List<Consultation> consultations;

    public Pharmacist() {
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }
}
