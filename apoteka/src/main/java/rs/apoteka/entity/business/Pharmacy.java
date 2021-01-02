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

}
