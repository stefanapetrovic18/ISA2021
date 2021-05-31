package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Offer;
import rs.apoteka.entity.user.PharmacyAdmin;
import rs.apoteka.repository.business.OfferRepository;
import rs.apoteka.service.intf.auth.AuthenticationService;
import rs.apoteka.service.intf.business.OfferService;
import rs.apoteka.service.intf.user.PharmacyAdminService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private PharmacyAdminService pharmacyAdminService;
    @Autowired
    private OfferRepository offerRepository;

    @Override
    public List<Offer> findAll() {
        return offerRepository.findAll();
    }

    @Override
    public List<Offer> findNonAccepted() {
        return findAll().stream().filter(o -> o.getAccepted().equals(false)).collect(Collectors.toList());
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
    public Offer accept(Offer offer) throws Exception {
        if (offer.getOrder().getExpiryDate().isAfter(LocalDateTime.now())) {
            throw new Exception("Rok za dostavljanje ponuda nije istekao!");
        }
        PharmacyAdmin admin = pharmacyAdminService.findByUsername(authenticationService.getUsername());
        if (admin == null) {
            throw new Exception("Administrator ne postoji!");
        }
        offer.setAccepted(true);
        sendAcceptedEmail(offer);
        return update(offer);
    }

    @Override
    public Offer reject(Offer offer) throws Exception {
        if (offer.getOrder().getExpiryDate().isAfter(LocalDateTime.now())) {
            throw new Exception("Rok za dostavljanje ponuda nije istekao!");
        }
        PharmacyAdmin admin = pharmacyAdminService.findByUsername(authenticationService.getUsername());
        if (admin == null) {
            throw new Exception("Administrator ne postoji!");
        }
        offer.setAccepted(false);
        sendRejectedEmail(offer);
        return update(offer);
    }

    private void sendAcceptedEmail(Offer offer) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(offer.getSupplier().getUsername());
        email.setSubject("Status ponude ID=" + offer.getId());
        email.setText("Poštovani/a " + offer.getSupplier().getForename() + ",\n\n" +
                "Obaveštavamo vas da je vaša ponuda ID=" + offer.getId() + " prihvaćena.\n\n" +
                "Srdačan pozdrav,\n\n" +
                "ISA");
        mailSender.send(email);
    }

    private void sendRejectedEmail(Offer offer) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(offer.getSupplier().getUsername());
        email.setSubject("Status ponude ID=" + offer.getId());
        email.setText("Poštovani/a " + offer.getSupplier().getForename() + ",\n\n" +
                "Obaveštavamo vas da vaša ponuda ID=" + offer.getId() + " nije prihvaćena.\n\n" +
                "Srdačan pozdrav,\n\n" +
                "ISA");
        mailSender.send(email);
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
