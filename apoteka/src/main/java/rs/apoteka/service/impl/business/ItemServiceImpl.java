package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Item;
import rs.apoteka.repository.business.ItemRepository;
import rs.apoteka.service.intf.business.ItemService;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public List<Item> findAllParametrized(Long id, Long medicineID, Double price, Double priceFrom, Double priceTo,
                                          Integer quantity, Integer quantityFrom, Integer quantityTo, Long pricelistID) {
        List<Item> items = findAll();
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
        if (quantity != null) {
            items.removeIf(i -> i.getQuantity().equals(quantity));
        }
        if (quantityFrom != null) {
            items.removeIf(i -> i.getQuantity() > quantityFrom);
        }
        if (quantityTo != null) {
            items.removeIf(i -> i.getQuantity() > quantityTo);
        }
        return items;
    }

    @Override
    public Item getOne(Long id) {
        return itemRepository.getOne(id);
    }

    @Override
    public Item create(Item item) {
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
