package rs.apoteka.entity.user;

import rs.apoteka.entity.auth.User;
import rs.apoteka.entity.business.Pharmacy;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class PharmacyAdmin extends User {
    @ManyToMany
    private List<Pharmacy> pharmacies;
}
