package rs.apoteka.entity.business;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Pricelist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Double consultationPrice;
    @Column(nullable = false)
    private Double examinationPrice;
    @OneToMany(mappedBy = "pricelist")
    List<Item> items;

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

}
