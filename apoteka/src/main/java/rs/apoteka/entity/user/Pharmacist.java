package rs.apoteka.entity.user;

import rs.apoteka.entity.auth.User;
import rs.apoteka.entity.business.Pharmacy;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Pharmacist extends User {
    @ManyToOne
    @JoinColumn(name = "pharmacy", referencedColumnName = "id")
    private Pharmacy pharmacy;
}
