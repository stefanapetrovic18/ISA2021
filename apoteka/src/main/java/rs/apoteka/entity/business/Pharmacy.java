package rs.apoteka.entity.business;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import rs.apoteka.entity.auth.User;
import rs.apoteka.entity.user.Dermatologist;
import rs.apoteka.entity.user.Patient;
import rs.apoteka.entity.user.Pharmacist;
import rs.apoteka.entity.user.PharmacyAdmin;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Pharmacy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@JsonView(Views.Public.class)
    private Long id;
    @Column(nullable = false, unique = true)
    //@JsonView(Views.Public.class)
    private String name;
    @Column(nullable = false)
    //@JsonView(Views.Public.class)
    private String description;
    @Column(nullable = false)
    //@JsonView(Views.Public.class)
    private String address;
    @JsonIgnore
    @OneToMany(mappedBy = "pharmacy")
    //@JsonView(Views.Internal.class)
    private List<PharmacyAdmin> admins;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JsonIgnore
    //@JsonView(Views.Internal.class)
    private List<Dermatologist> dermatologists;
    @JsonIgnore
    @OneToMany(mappedBy = "pharmacy")
    //@JsonView(Views.Internal.class)
    private List<Pharmacist> pharmacists;
    @JsonIgnore
    @OneToMany(mappedBy = "pharmacy")
    //@JsonView(Views.Internal.class)
    private List<Examination> examinations;
    @JsonIgnore
    @OneToMany(mappedBy = "pharmacy")
    //@JsonView(Views.Internal.class)
    private List<Consultation> consultations;
    @JsonIgnore
    @OneToMany(mappedBy = "pharmacy")
    //@JsonView(Views.Internal.class)
    private List<Promotion> promotions;
    @JsonIgnore
    @OneToMany(mappedBy = "pharmacy")
    //@JsonView(Views.Internal.class)
    private List<Order> orders;
    @JsonIgnore
    @ElementCollection(targetClass = User.class)
    //@JsonView(Views.Internal.class)
    private List<Patient> subscriptions;
    // Inventar.
    @OneToOne
    //@JsonView(Views.Public.class)
    private Pricelist pricelist;
    @OneToMany(mappedBy = "pharmacy")
    //@JsonView(Views.Public.class)
    private List<Stockpile> stockpile;
    @Column
    //@JsonView(Views.Public.class)
    private Double rating;

    @ManyToMany
    private List<Rating> ratings;

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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Patient> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Patient> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Pricelist getPricelist() {
        return pricelist;
    }

    public void setPricelist(Pricelist pricelist) {
        this.pricelist = pricelist;
    }

    public List<Stockpile> getStockpile() {
        return stockpile;
    }

    public void setStockpile(List<Stockpile> stockpile) {
        this.stockpile = stockpile;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
