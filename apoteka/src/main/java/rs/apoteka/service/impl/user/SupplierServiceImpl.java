package rs.apoteka.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.user.Supplier;
import rs.apoteka.repository.user.SupplierRepository;
import rs.apoteka.service.intf.user.SupplierService;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier getOne(Long id) {
        return supplierRepository.getOne(id);
    }

    @Override
    public Supplier create(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier update(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public Boolean delete(Long id) {
        supplierRepository.deleteById(id);
        return true;
    }
}
