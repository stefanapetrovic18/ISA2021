package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.Offer;

import java.time.LocalDateTime;
import java.util.List;

public interface OfferService {
    List<Offer> findAll();

    List<Offer> findNonAccepted();

    List<Offer> findAllParametrized(Long id, Long orderID, LocalDateTime shippingDate, LocalDateTime shippingDateFrom, LocalDateTime shippingDateUntil);

    Offer getOne(Long id);

    Offer accept(Offer offer) throws Exception;

    Offer reject(Offer offer) throws Exception;

    Offer create(Offer offer);

    Offer update(Offer offer);

    Boolean delete(Long id);
}
