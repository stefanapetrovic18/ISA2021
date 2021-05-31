package rs.apoteka.entity.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import rs.apoteka.entity.user.Patient;
import rs.apoteka.entity.user.Pharmacist;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@JsonView(Views.Public.class)
    private Long id;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    //@JsonView(Views.Public.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime consultationDate;
    @ManyToOne(optional = false)
    @JoinColumn(name = "pharmacist", referencedColumnName = "id")
    //@JsonView(Views.Public.class)
    private Pharmacist pharmacist;
    @ManyToOne(optional = false)
    @JoinColumn(name = "pharmacy", referencedColumnName = "id")
    //@JsonView(Views.Public.class)
    private Pharmacy pharmacy;
    @ManyToOne
    @JoinColumn(name = "patient", referencedColumnName = "id")
    //@JsonView(Views.Public.class)
    private Patient patient;
    @Column(nullable = false)
    //@JsonView(Views.Public.class)
    private Integer duration;
    @Column(nullable = false)
    //@JsonView(Views.Public.class)
    private Double price;

    public Consultation() {
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getConsultationDate() {
        return consultationDate;
    }

    public void setConsultationDate(LocalDateTime consultationDate) {
        this.consultationDate = consultationDate;
    }

    public Pharmacist getPharmacist() {
        return pharmacist;
    }

    public void setPharmacist(Pharmacist pharmacist) {
        this.pharmacist = pharmacist;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
