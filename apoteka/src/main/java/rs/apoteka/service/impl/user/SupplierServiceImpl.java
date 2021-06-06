package rs.apoteka.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
    public Supplier create(Supplier supplier) {
        supplier.setEnabled(false);
        supplier.setPasswordChanged(false);
        supplier.setValidated(false);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        supplier.setPassword(encoder.encode(supplier.getPassword()));
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
