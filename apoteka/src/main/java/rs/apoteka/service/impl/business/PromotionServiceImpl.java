package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Promotion;
import rs.apoteka.repository.business.PromotionRepository;
import rs.apoteka.service.intf.business.PromotionService;

import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public List<Promotion> findAll() {
        return promotionRepository.findAll();
    }

    @Override
    public Promotion getOne(Long id) {
        return promotionRepository.getOne(id);
    }

    @Override
    public Promotion create(Promotion promotion) {
        if (!checkDates(promotion)) {
            return null;
        }
        // Posalji email.
        return promotionRepository.save(promotion);
    }

    @Override
    public Promotion update(Promotion promotion) {
        if (!checkDates(promotion)) {
            return null;
        }
        return promotionRepository.save(promotion);
    }

    private Boolean checkDates(Promotion promotion) {
        return promotion.getStartDate().isBefore(promotion.getEndDate());
    }

    @Override
    public Boolean delete(Long id) {
        promotionRepository.deleteById(id);
        return true;
    }
}
