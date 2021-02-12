package rs.apoteka.entity.business;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Pricelist {
    @OneToMany(mappedBy = "pricelist")
    List<Item> items;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Double consultationPrice;
    @Column(nullable = false)
    private Double examinationPrice;
    @Column(nullable = false)
    private LocalDateTime validFrom;
    @Column(nullable = false)
    private LocalDateTime validUntil;

    public Pricelist() {
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public Double getConsultationPrice() {
        return consultationPrice;
    }

    public void setConsultationPrice(Double consultationPrice) {
        this.consultationPrice = consultationPrice;
    }

    public Double getExaminationPrice() {
        return examinationPrice;
    }

    public void setExaminationPrice(Double examinationPrice) {
        this.examinationPrice = examinationPrice;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDateTime validUntil) {
        this.validUntil = validUntil;
    }
}
