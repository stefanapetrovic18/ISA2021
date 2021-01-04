package rs.apoteka.entity.business;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Pricelist {
    @OneToMany(mappedBy = "pricelist")
    List<Item> items;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Date validFrom;
    @Column(nullable = false)
    private Date validUntil;

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

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }
}
