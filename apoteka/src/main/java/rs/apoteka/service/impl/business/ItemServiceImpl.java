package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Item;
import rs.apoteka.entity.business.Pricelist;
import rs.apoteka.entity.user.PharmacyAdmin;
import rs.apoteka.exception.DataMismatchException;
import rs.apoteka.repository.business.ItemRepository;
import rs.apoteka.service.intf.auth.AuthenticationService;
import rs.apoteka.service.intf.business.ItemService;
import rs.apoteka.service.intf.user.PharmacyAdminService;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private PharmacyAdminService pharmacyAdminService;

    @Override
    public List<Item> findAll() throws Exception {
        PharmacyAdmin admin = pharmacyAdminService.findByUsername(authenticationService.getUsername());
        if (admin == null) {
            throw new Exception("Administrator apoteke nije ulogovan!");
        }
        return admin.getPharmacy().getPricelist().getItems();
    }

    @Override
    public List<Item> findAllParametrized(Long id, Long medicineID, Double price, Double priceFrom, Double priceTo,
                                          Integer quantity, Integer quantityFrom, Integer quantityTo, Long pricelistID) {
        List<Item> items = itemRepository.findAll();
        if (id != null) {
            items.removeIf(i -> !i.getId().equals(id));
        }
        if (medicineID != null) {
            items.removeIf(i -> !i.getMedicine().getId().equals(medicineID));
        }
        if (pricelistID != null) {
            items.removeIf(i -> !i.getPricelist().getId().equals(pricelistID));
        }
        if (price != null) {
            items.removeIf(i -> i.getPrice().equals(price));
        }
        if (priceFrom != null) {
            items.removeIf(i -> i.getPrice() > priceFrom);
        }
        if (priceTo != null) {
            items.removeIf(i -> i.getPrice() > priceTo);
        }
        return items;
    }

    @Override
    public Double getPriceAtDate(List<Item> items, Long medicineID, LocalDate priceFrom, LocalDate priceUntil) {
        if (medicineID != null) {
            items.removeIf(i -> !i.getMedicine().getId().equals(medicineID));
        }
        if (items == null || items.isEmpty()) {
            return 0.0;
        }
        if (priceFrom != null && priceUntil != null) {
            items.removeIf(i -> i.getValidFrom().isAfter(priceUntil.atStartOfDay()) || i.getValidUntil().isAfter(priceFrom.atStartOfDay()));
        }
        if (items.isEmpty()) {
            return 0.0;
        }
        return items.get(0).getPrice();
    }

    @Override
    public Item getOne(Long id) {
        return itemRepository.getOne(id);
    }

    @Override
    public Item create(Item item) throws DataMismatchException {
        if (item.getValidFrom().isAfter(item.getValidUntil())) {
            throw new DataMismatchException("Datumi se ne poklapaju!");
        }
        PharmacyAdmin admin = pharmacyAdminService.findByUsername(authenticationService.getUsername());
        Pricelist pricelist = admin.getPharmacy().getPricelist();
        if (admin.getPharmacy() == null) {
            throw new EntityNotFoundException("Apoteka ne postoji.");
        }
        if (pricelist != null) {
            for (Item i : pricelist.getItems()) {
                if (i.getMedicine().getId().equals(item.getMedicine().getId())) {
                    if(!(i.getValidFrom().isAfter(item.getValidUntil()) || i.getValidUntil().isBefore(item.getValidFrom()))) {
                        throw new DataMismatchException("Već je definisan datum važenja cene u ovom periodu!");
                    }
                }
            }
        }
        item.setPricelist(pricelist);
        return itemRepository.save(item);
    }

    @Override
    public Item update(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Boolean delete(Long id) {
        itemRepository.deleteById(id);
        return true;
    }
}
