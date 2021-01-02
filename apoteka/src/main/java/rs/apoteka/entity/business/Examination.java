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
}
