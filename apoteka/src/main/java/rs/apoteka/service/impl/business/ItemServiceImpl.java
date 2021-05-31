package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Item;
import rs.apoteka.entity.business.Pricelist;
import rs.apoteka.entity.user.PharmacyAdmin;
import rs.apoteka.repository.business.ItemRepository;
import rs.apoteka.service.intf.auth.AuthenticationService;
import rs.apoteka.service.intf.business.ItemService;
import rs.apoteka.service.intf.user.PharmacyAdminService;

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
    public Item getOne(Long id) {
        return itemRepository.getOne(id);
    }

    @Override
    public Item create(Item item) {
        PharmacyAdmin admin = pharmacyAdminService.findByUsername(authenticationService.getUsername());
        Pricelist pricelist = admin.getPharmacy().getPricelist();
        if (pricelist != null) {
            for (Item i : pricelist.getItems()) {
                if (i.getMedicine().getId().equals(item.getMedicine().getId())) {
                    i.setPrice(item.getPrice());
                    return update(i);
                }
            }
        }
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
