package rs.apoteka.entity.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.web.bind.annotation.GetMapping;

import rs.apoteka.entity.user.Patient;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@JsonView(Views.Public.class)
    private Long id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    //@JsonView(Views.Public.class)
    private String reservationNumber;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    //@JsonView(Views.Public.class)
    private LocalDateTime reservationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    //@JsonView(Views.Public.class)
    private LocalDateTime collectionDate;
    @ManyToOne(optional = false)
    @JoinColumn(name = "pharmacy", referencedColumnName = "id")
    //@JsonView(Views.Public.class)
    private Pharmacy pharmacy;
    @ManyToOne(optional = false)
    @JoinColumn(name = "patient", referencedColumnName = "id")
    //@JsonView(Views.Public.class)
    private Patient patient;
    @ManyToOne(optional = false)
    @JoinColumn(name = "medicine", referencedColumnName = "id")
    //@JsonView(Views.Public.class)
    private Medicine medicine;
    @Column(nullable = false)
    //@JsonView(Views.Public.class)
    private Boolean collected;
    @Column(nullable = false)
    private Boolean penalized;

    public Reservation() {
    }

    public Long getId() {
        return id;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public LocalDateTime getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(LocalDateTime collectionDate) {
        this.collectionDate = collectionDate;
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

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public Boolean getCollected() {
        return collected;
    }

    public void setCollected(Boolean collected) {
        this.collected = collected;
    }

    public Boolean getPenalized() {
        return penalized;
    }

    public void setPenalized(Boolean penalized) {
        this.penalized = penalized;
    }
}
