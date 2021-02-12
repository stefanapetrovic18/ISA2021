package rs.apoteka.entity.business;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
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
    @ManyToMany(cascade = CascadeType.MERGE, targetEntity = Medicine.class)
    //@JsonIgnore
    private List<Medicine> substitutes;

    public Medicine() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public Boolean getPrescriptionNecessary() {
        return prescriptionNecessary;
    }

    public void setPrescriptionNecessary(Boolean prescriptionNecessary) {
        this.prescriptionNecessary = prescriptionNecessary;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getRecommendedDose() {
        return recommendedDose;
    }

    public void setRecommendedDose(String recommendedDose) {
        this.recommendedDose = recommendedDose;
    }

    public List<Medicine> getSubstitutes() {
        return substitutes;
    }

    public void setSubstitutes(List<Medicine> substitutes) {
        this.substitutes = substitutes;
    }
}
