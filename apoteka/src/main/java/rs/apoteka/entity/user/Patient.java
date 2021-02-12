package rs.apoteka.entity.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import rs.apoteka.entity.auth.RegistrationRequest;
import rs.apoteka.entity.auth.Role;
import rs.apoteka.entity.auth.RoleType;
import rs.apoteka.entity.auth.User;
import rs.apoteka.entity.business.Consultation;
import rs.apoteka.entity.business.Examination;
import rs.apoteka.entity.business.Medicine;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;

@Entity
@Table
public class Patient extends User {
    @Column(nullable = false)
    //@JsonView(Views.Public.class)
    private Integer points;
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    //@JsonView(Views.Internal.class)
    private List<Examination> examinations;
    @OneToMany(mappedBy = "patient")
    //@JsonView(Views.Internal.class)
    @JsonIgnore
    private List<Consultation> consultations;
    @ManyToMany
    //@JsonView(Views.Internal.class)
    private List<Medicine> allergies;

    public Patient() {
    }

    public Patient(RegistrationRequest request) {
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
            add(new Role(RoleType.ROLE_PATIENT));
        }});
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
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

    public List<Medicine> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<Medicine> allergies) {
        this.allergies = allergies;
    }
}
