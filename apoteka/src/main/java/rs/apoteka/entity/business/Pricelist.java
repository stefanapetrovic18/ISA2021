package rs.apoteka.entity.business;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Pricelist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Date validFrom;
    @Column(nullable = false)
    private Date validUntil;
    @OneToMany(mappedBy = "pricelist")
    List<Item> items;
}
