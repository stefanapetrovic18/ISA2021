package rs.apoteka.entity.business;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@JsonView(Views.Public.class)
    private Long id;
    @Column(nullable = false)
    //@JsonView(Views.Public.class)
    private String name;
    @Column(nullable = false)
    //@JsonView(Views.Public.class)
    private String manufacturer;
    @Column(nullable = false)
    //@JsonView(Views.Public.class)
    private String code;
    @Column(nullable = false)
    //@JsonView(Views.Public.class)
    private String type;
    @Column(nullable = false)
    //@JsonView(Views.Public.class)
    private String form;
    @Column(nullable = false)
    //@JsonView(Views.Public.class)
    private Boolean prescriptionNecessary;
    // Specification.
    @Column(nullable = false)
    //@JsonView(Views.Public.class)
    private String sideEffects;
    @Column(nullable = false)
    @ElementCollection(targetClass = String.class)
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    //@JsonView(Views.Public.class)
    private List<String> ingredients;
    @Column(nullable = false)
    //@JsonView(Views.Public.class)
    private String recommendedDose;
    @ManyToMany(cascade = CascadeType.MERGE, targetEntity = Medicine.class)
    @JsonIgnore
    //@JsonView(Views.Internal.class)
    private List<Medicine> substitutes;

    @ManyToMany(cascade = CascadeType.MERGE, targetEntity = Rating.class)
    private List<Rating> ratings;
    @Column
    private Double rating;

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

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
