package rs.apoteka.entity.business;

import rs.apoteka.entity.user.Supplier;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<InventoryItem> items;
    @OneToOne(mappedBy = "inventory")
    private Supplier supplier;

    public Inventory() {
    }

    public Long getId() {
        return id;
    }

    public List<InventoryItem> getItems() {
        return items;
    }

    public void setItems(List<InventoryItem> items) {
        this.items = items;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
