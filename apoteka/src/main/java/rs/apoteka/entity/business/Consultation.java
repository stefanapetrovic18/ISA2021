package rs.apoteka.entity.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import rs.apoteka.entity.user.Patient;
import rs.apoteka.entity.user.Pharmacist;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date consultationDate;
    @ManyToOne(optional = false)
    @JoinColumn(name = "pharmacist", referencedColumnName = "id")
    private Pharmacist pharmacist;
    @ManyToOne(optional = false)
    @JoinColumn(name = "pharmacy", referencedColumnName = "id")
    private Pharmacy pharmacy;
    @ManyToOne(optional = false)
    @JoinColumn(name = "patient", referencedColumnName = "id")
    private Patient patient;
    @Column(nullable = false)
    private Integer duration;
    @Column(nullable = false)
    private Double price;

    public Consultation() {
    }

    public Long getId() {
        return id;
    }

    public Date getConsultationDate() {
        return consultationDate;
    }

    public void setConsultationDate(Date consultationDate) {
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
