package rs.apoteka.entity.user;

import rs.apoteka.entity.auth.User;
import rs.apoteka.entity.business.Examination;
import rs.apoteka.entity.business.Pharmacy;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Dermatologist extends User {
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "pharmacy", referencedColumnName = "id")
    private Pharmacy pharmacy;
    @OneToMany(mappedBy = "dermatologist", cascade = CascadeType.ALL)
    private List<Examination> appointments;
}
