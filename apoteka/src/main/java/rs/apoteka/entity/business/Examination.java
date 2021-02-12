package rs.apoteka.entity.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import rs.apoteka.entity.user.Dermatologist;
import rs.apoteka.entity.user.Patient;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Examination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime examinationDate;
    @ManyToOne(optional = false)
    @JoinColumn(name = "dermatologist", referencedColumnName = "id")
    private Dermatologist dermatologist;
    @ManyToOne(optional = false)
    @JoinColumn(name = "patient", referencedColumnName = "id")
    private Patient patient;
    @ManyToOne(optional = false)
    @JoinColumn(name = "pharmacy", referencedColumnName = "id")
    private Pharmacy pharmacy;
    @Column(nullable = false)
    private Integer duration;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Boolean quickReservation;

    public Examination() {
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getExaminationDate() {
        return examinationDate;
    }

    public void setExaminationDate(LocalDateTime examinationDate) {
        this.examinationDate = examinationDate;
    }

    public Dermatologist getDermatologist() {
        return dermatologist;
    }

    public void setDermatologist(Dermatologist dermatologist) {
        this.dermatologist = dermatologist;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
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

    public Boolean getQuickReservation() {
        return quickReservation;
    }

    public void setQuickReservation(Boolean quickReservation) {
        this.quickReservation = quickReservation;
    }
}
