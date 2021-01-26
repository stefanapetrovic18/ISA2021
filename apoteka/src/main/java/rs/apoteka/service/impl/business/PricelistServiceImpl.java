package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Pricelist;
import rs.apoteka.repository.business.PricelistRepository;
import rs.apoteka.service.intf.business.PricelistService;

import java.util.List;

@Service
public class PricelistServiceImpl implements PricelistService {
    @Autowired
    private PricelistRepository pricelistRepository;

    @Override
    public List<Pricelist> findAll() {
        return pricelistRepository.findAll();
    }

    @Override
    public Pricelist getOne(Long id) {
        return pricelistRepository.getOne(id);
    }

    @Override
    public Pricelist create(Pricelist pricelist) {
        if (!checkDates(pricelist)) {
            return null;
        }
        return pricelistRepository.save(pricelist);
    }

    @Override
    public Pricelist update(Pricelist pricelist) {
        if (!checkDates(pricelist)) {
            return null;
        }
        return pricelistRepository.save(pricelist);
    }

    private Boolean checkDates(Pricelist pricelist) {
        return pricelist.getValidFrom().isBefore(pricelist.getValidUntil());
    }

    @Override
    public Boolean delete(Long id) {
        pricelistRepository.deleteById(id);
        return true;
    }
}
