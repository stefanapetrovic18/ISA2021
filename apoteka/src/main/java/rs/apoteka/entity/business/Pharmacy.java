package rs.apoteka.entity.business;

import rs.apoteka.entity.user.Dermatologist;
import rs.apoteka.entity.user.Pharmacist;
import rs.apoteka.entity.user.PharmacyAdmin;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Pharmacy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<PharmacyAdmin> admins;
    @ManyToMany
    private List<Dermatologist> dermatologists;
    @OneToMany(mappedBy = "pharmacy")
    private List<Pharmacist> pharmacists;
    @OneToMany(mappedBy = "pharmacy")
    private List<Examination> examinations;
    @OneToMany(mappedBy = "pharmacy")
    private List<Consultation> consultations;
    // Inventar.
    @OneToOne
    private Pricelist pricelist;

    public Pharmacy() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PharmacyAdmin> getAdmins() {
        return admins;
    }

    public void setAdmins(List<PharmacyAdmin> admins) {
        this.admins = admins;
    }

    public List<Dermatologist> getDermatologists() {
        return dermatologists;
    }

    public void setDermatologists(List<Dermatologist> dermatologists) {
        this.dermatologists = dermatologists;
    }

    public List<Pharmacist> getPharmacists() {
        return pharmacists;
    }

    public void setPharmacists(List<Pharmacist> pharmacists) {
        this.pharmacists = pharmacists;
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

    public Pricelist getPricelist() {
        return pricelist;
    }

    public void setPricelist(Pricelist pricelist) {
        this.pricelist = pricelist;
    }
}
