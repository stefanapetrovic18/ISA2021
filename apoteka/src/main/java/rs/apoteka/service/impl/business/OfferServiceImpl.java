package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Offer;
import rs.apoteka.repository.business.OfferRepository;
import rs.apoteka.service.intf.business.OfferService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    @Autowired
    private OfferRepository offerRepository;

    @Override
    public List<Offer> findAll() {
        return offerRepository.findAll();
    }

    @Override
    public List<Offer> findAllParametrized(Long id, Long orderID, LocalDateTime shippingDate, LocalDateTime shippingDateFrom, LocalDateTime shippingDateUntil) {
        List<Offer> offer = findAll();
        if (id != null) {
            offer.removeIf(p -> !p.getId().equals(id));
        }
        if (orderID != null) {
            offer.removeIf(p -> !p.getOrder().getId().equals(orderID));
        }
        if (shippingDate != null) {
            offer.removeIf(p -> !p.getShippingDate().equals(shippingDate));
        }
        if (shippingDateFrom != null) {
            offer.removeIf(p -> p.getShippingDate().isBefore(shippingDateFrom));
        }
        if (shippingDateUntil != null) {
            offer.removeIf(p -> p.getShippingDate().isAfter(shippingDateUntil));
        }
        return offer;
    }

    @Override
    public Offer getOne(Long id) {
        return offerRepository.getOne(id);
    }

    @Override
    public Offer create(Offer offer) {
        return offerRepository.save(offer);
    }

    @Override
    public Offer update(Offer offer) {
        return offerRepository.save(offer);
    }

    @Override
    public Boolean delete(Long id) {
        offerRepository.deleteById(id);
        return true;
    }
}
