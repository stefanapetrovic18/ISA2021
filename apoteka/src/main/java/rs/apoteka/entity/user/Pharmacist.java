package rs.apoteka.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import rs.apoteka.entity.auth.RegistrationRequest;
import rs.apoteka.entity.auth.Role;
import rs.apoteka.entity.auth.RoleType;
import rs.apoteka.entity.auth.User;
import rs.apoteka.entity.business.Consultation;
import rs.apoteka.entity.business.Pharmacy;
import rs.apoteka.entity.business.WorkingHours;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Entity
@Table
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Pharmacist extends User {
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime vacationStart;
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime vacationEnd;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pharmacy", referencedColumnName = "id")
    private Pharmacy pharmacy;
    @OneToMany(mappedBy = "pharmacist", cascade = CascadeType.ALL)
    private List<Consultation> consultations;
    @Column
    @ElementCollection(targetClass = WorkingHours.class)
    private List<WorkingHours> workingHours;
    @Column
    private Double rating;

    public Pharmacist() {
    }

    public Pharmacist(RegistrationRequest request) {
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
            add(new Role(RoleType.ROLE_PHARMACIST));
        }});
    }

    public Pharmacist(User user) {
        super(user.getUsername(), user.getPassword(), user.getForename(), user.getSurname(),
                user.getAddress(), user.getCity(), user.getCountry(), user.getPhone(),
                user.getPasswordChanged(), user.getEnabled(), user.getValidated(), user.getRoles());

    }

    public LocalDateTime getVacationStart() {
        return vacationStart;
    }

    public void setVacationStart(LocalDateTime vacationStart) {
        this.vacationStart = vacationStart;
    }

    public LocalDateTime getVacationEnd() {
        return vacationEnd;
    }

    public void setVacationEnd(LocalDateTime vacationEnd) {
        this.vacationEnd = vacationEnd;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

    public List<WorkingHours> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(List<WorkingHours> workingHours) {
        this.workingHours = workingHours;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
