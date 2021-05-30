package rs.apoteka.entity.business;

import rs.apoteka.entity.user.Patient;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "patient", referencedColumnName = "id")
    private Patient patient;
    @Column(nullable = false)
    @Min(1)
    @Max(5)
    private Integer rating;
}
