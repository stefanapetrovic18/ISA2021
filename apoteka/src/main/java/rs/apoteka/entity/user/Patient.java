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
}
