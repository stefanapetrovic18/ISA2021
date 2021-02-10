package rs.apoteka.entity.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import rs.apoteka.entity.auth.User;
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
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String address;
    @JsonIgnore
    @OneToMany(mappedBy = "pharmacy")
    private List<PharmacyAdmin> admins;
    @JsonIgnore
    @ManyToMany
    private List<Dermatologist> dermatologists;
    @JsonIgnore
    @OneToMany(mappedBy = "pharmacy")
    private List<Pharmacist> pharmacists;
    @JsonIgnore
    @OneToMany(mappedBy = "pharmacy")
    private List<Examination> examinations;
    @JsonIgnore
    @OneToMany(mappedBy = "pharmacy")
    private List<Consultation> consultations;
    @JsonIgnore
    @OneToMany(mappedBy = "pharmacy")
    private List<Promotion> promotions;
    @JsonIgnore
    @ElementCollection(targetClass = User.class)
    private List<User> subscriptions;
    // Inventar.
    @OneToOne
    private Pricelist pricelist;
    @Column
    private Double rating;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<User> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<User> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Pricelist getPricelist() {
        return pricelist;
    }

    public void setPricelist(Pricelist pricelist) {
        this.pricelist = pricelist;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
