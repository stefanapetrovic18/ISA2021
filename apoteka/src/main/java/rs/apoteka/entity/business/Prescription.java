package rs.apoteka.entity.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import rs.apoteka.entity.user.Patient;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date issueDate;
    @ManyToOne(optional = false)
    @JoinColumn(name = "pharmacy", referencedColumnName = "id")
    private Pharmacy pharmacy;
    @ManyToOne(optional = false)
    @JoinColumn(name = "patient", referencedColumnName = "id")
    private Patient patient;
    @ElementCollection
    private List<Medicine> medicines;
}
