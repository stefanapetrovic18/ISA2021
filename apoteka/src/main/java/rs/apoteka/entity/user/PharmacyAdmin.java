package rs.apoteka.entity.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import rs.apoteka.entity.auth.RegistrationRequest;
import rs.apoteka.entity.auth.Role;
import rs.apoteka.entity.auth.RoleType;
import rs.apoteka.entity.auth.User;
import rs.apoteka.entity.business.Pharmacy;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.HashSet;

@Entity
@Table
public class PharmacyAdmin extends User {
    @ManyToOne
    @JoinColumn(name = "pharmacy", referencedColumnName = "id")
    private Pharmacy pharmacy;

    public PharmacyAdmin() {
    }

    public PharmacyAdmin(RegistrationRequest request) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.setUsername(request.getUsername());
        this.setPassword(encoder.encode(request.getPassword()));
        this.setForename(request.getForename());
        this.setSurname(request.getSurname());
        this.setAddress(request.getAddress());
        this.setCity(request.getCity());
        this.setCountry(request.getCountry());
        this.setPhone(request.getPhone());
        this.setPasswordChanged(false);
        this.setEnabled(true);
        this.setValidated(false);
        this.setRoles(new HashSet<>() {{
            add(new Role(RoleType.ROLE_PHARMACY_ADMIN));
        }});
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }
}
