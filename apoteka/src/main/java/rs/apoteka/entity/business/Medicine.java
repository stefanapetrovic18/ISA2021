package rs.apoteka.entity.business;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String manufacturer;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String form;
    @Column(nullable = false)
    private Boolean prescriptionNecessary;
    // Specification.
    @Column(nullable = false)
    private String sideEffects;
    @Column(nullable = false)
    @ElementCollection(targetClass = String.class)
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    private List<String> ingredients;
    @Column(nullable = false)
    private String recommendedDose;
    @ManyToMany(cascade = CascadeType.ALL, targetEntity = Medicine.class)
    private List<Medicine> substitutes;
}
