package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Stockpile;
import rs.apoteka.entity.user.PharmacyAdmin;
import rs.apoteka.exception.DataMismatchException;
import rs.apoteka.exception.UserNotFoundException;
import rs.apoteka.repository.business.StockpileRepository;
import rs.apoteka.service.intf.auth.AuthenticationService;
import rs.apoteka.service.intf.business.StockpileService;
import rs.apoteka.service.intf.user.PharmacyAdminService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class StockpileServiceImpl implements StockpileService {
    @Autowired
    private StockpileRepository stockpileRepository;
    @Autowired
    private PharmacyAdminService pharmacyAdminService;
    @Autowired
    private AuthenticationService authenticationService;
    @Override
    public List<Stockpile> findAll() {
        return stockpileRepository.findAll();
    }

    @Override
    public List<Stockpile> findAllParametrized(Long id, Long medicineID, Long pharmacyID, Integer quantity, Integer quantityFrom, Integer quantityTo) {
        List<Stockpile> stockpileList = findAll();
        if (id != null) {
            stockpileList.removeIf(s -> !s.getId().equals(id));
        }
        if (medicineID != null) {
            stockpileList.removeIf(s -> !s.getMedicine().getId().equals(medicineID));
        }
        if (pharmacyID != null) {
            stockpileList.removeIf(s -> !s.getPharmacy().getId().equals(id));
        }
        if (quantity != null) {
            stockpileList.removeIf(s -> !s.getQuantity().equals(quantity));
        }
        if (quantityFrom != null) {
            stockpileList.removeIf(s -> s.getQuantity() < quantityFrom);
        }
        if (quantityTo != null) {
            stockpileList.removeIf(s -> s.getQuantity() > quantityTo);
        }
        return stockpileList;
    }

    @Override
    public Stockpile getOne(Long id) {
        return stockpileRepository.getOne(id);
    }

    @Override
    public Stockpile create(Stockpile stockpile) throws UserNotFoundException, DataMismatchException {
        PharmacyAdmin admin = pharmacyAdminService.findByUsername(authenticationService.getUsername());
        if (admin == null) {
            throw new UserNotFoundException();
        }
        stockpile.setPharmacy(admin.getPharmacy());
        List<Stockpile> stck = findAllParametrized(null, stockpile.getMedicine().getId(), stockpile.getPharmacy().getId(),
                null, null, null);
        if (stck != null && !stck.isEmpty()) {
            Stockpile s = stck.get(0);
            s.setQuantity(stockpile.getQuantity());
            return update(s);
        }
        return stockpileRepository.save(stockpile);
    }

    @Override
    public Stockpile update(Stockpile stockpile) throws UserNotFoundException, DataMismatchException {
        PharmacyAdmin admin = pharmacyAdminService.findByUsername(authenticationService.getUsername());
        if (admin == null) {
            throw new UserNotFoundException();
        }
        if (!stockpile.getPharmacy().getId().equals(admin.getPharmacy().getId())) {
            throw new DataMismatchException("Administrator apoteke ne može da izmeni zalihe tuđe apoteke.");
        }
        Stockpile s = getOne(stockpile.getId());
        if (s == null) {
            throw new EntityNotFoundException("Zalihe ne postoje.");
        }
        s.setQuantity(stockpile.getQuantity());
        return stockpileRepository.save(s);
    }

    @Override
    public Boolean delete(Long id) {
        stockpileRepository.deleteById(id);
        return getOne(id) == null;
    }
}
