package rs.apoteka.entity.business;

import javax.persistence.*;

@Entity
@Table
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(optional = false)
    @JoinColumn(name = "medicine", referencedColumnName = "id")
    private Medicine medicine;
    @Column
    private Double price;
    @Column
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "pricelist", referencedColumnName = "id")
    private Pricelist pricelist;
}
