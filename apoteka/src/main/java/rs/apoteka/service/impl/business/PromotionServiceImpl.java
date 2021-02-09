package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Pricelist;
import rs.apoteka.entity.business.Promotion;
import rs.apoteka.repository.business.PromotionRepository;
import rs.apoteka.service.intf.business.PromotionService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public List<Promotion> findAll() {
        return promotionRepository.findAll();
    }

    @Override
    public List<Promotion> findAllParametrized(Long id, LocalDateTime startDate, LocalDateTime endDate, Long pharmacyID,
                                               String title, String description) {
        List<Promotion> promotions = findAll();
        if (id != null) {
            promotions.removeIf(p -> !p.getId().equals(id));
        }
        if (pharmacyID != null) {
            promotions.removeIf(p -> !p.getPharmacy().getId().equals(pharmacyID));
        }
        if (title != null) {
            promotions.removeIf(p -> !p.getTitle().contains(title));
        }
        if (description != null) {
            promotions.removeIf(p -> !p.getDescription().contains(description));
        }
        if (startDate != null) {
            promotions.removeIf(p -> p.getStartDate().isBefore(startDate));
        }
        if (endDate != null) {
            promotions.removeIf(p -> p.getEndDate().isAfter(endDate));
        }
        return promotions;
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
        Promotion saved = promotionRepository.save(promotion);
        saved.getPharmacy().getSubscriptions().forEach(
                subscription -> {
                    SimpleMailMessage email = new SimpleMailMessage();
                    email.setTo(subscription.getUsername());
                    email.setSubject(promotion.getTitle());
                    email.setText("Postovani/a " + subscription.getForename() + ",\n\n" +
                            "Želimo da vas obavestimo o novoj promociji.\n\n" +
                            saved.getDescription() + "\n\n" +
                            "Da biste saznali više, molimo vas da kliknete na link ispod.\n\n" +
                            "http://localhost:4200/promocija?id=" + saved.getId() + "\n\n" +
                            "Srdačan pozdrav,\n\n" +
                            saved.getPharmacy().getName());
                    mailSender.send(email);
                }
        );
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
