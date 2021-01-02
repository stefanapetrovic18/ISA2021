package rs.apoteka.service.intf.user;

import rs.apoteka.entity.user.Supplier;

import java.util.List;

public interface SupplierService {
    List<Supplier> findAll();

    Supplier getOne(Long id);

    Supplier create(Supplier supplier);

    Supplier update(Supplier supplier);

    Boolean delete(Long id);
}
