package rs.apoteka.entity.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import rs.apoteka.entity.user.Dermatologist;
import rs.apoteka.entity.user.Patient;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Examination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date examinationDate;
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

    public Examination() {
    }

    public Long getId() {
        return id;
    }

    public Date getExaminationDate() {
        return examinationDate;
    }

    public void setExaminationDate(Date examinationDate) {
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
}
