package rs.apoteka.entity.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import rs.apoteka.entity.auth.RegistrationRequest;
import rs.apoteka.entity.auth.Role;
import rs.apoteka.entity.auth.RoleType;
import rs.apoteka.entity.auth.User;
import rs.apoteka.entity.business.Inventory;
import rs.apoteka.entity.business.Offer;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;

@Entity
@Table
public class Supplier extends User {

    @OneToOne

    private Inventory inventory;
    @OneToMany
    @JsonIgnore
    private List<Offer> offers;

    public Supplier(RegistrationRequest request) {
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
            add(new Role(RoleType.ROLE_SUPPLIER));
        }});
    }

    public Supplier() {
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}
