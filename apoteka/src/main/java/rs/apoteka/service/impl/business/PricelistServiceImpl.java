package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.Pricelist;
import rs.apoteka.entity.user.PharmacyAdmin;
import rs.apoteka.repository.business.PricelistRepository;
import rs.apoteka.service.intf.auth.AuthenticationService;
import rs.apoteka.service.intf.business.PharmacyService;
import rs.apoteka.service.intf.business.PricelistService;
import rs.apoteka.service.intf.user.PharmacyAdminService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PricelistServiceImpl implements PricelistService {
    @Autowired
    private PricelistRepository pricelistRepository;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private PharmacyAdminService pharmacyAdminService;
    @Autowired
    private PharmacyService pharmacyService;

    @Override
    public List<Pricelist> findAll() throws Exception {
        PharmacyAdmin admin = pharmacyAdminService.findByUsername(authenticationService.getUsername());
        if (admin == null) {
            throw new Exception("Administrator apoteke nije ulogovan!");
        }
        return Collections.singletonList(admin.getPharmacy().getPricelist());
    }

    @Override
    public List<Pricelist> findAllParametrized(Long id, Long itemID, LocalDateTime validFrom, LocalDateTime validUntil) {
        List<Pricelist> pricelist = pricelistRepository.findAll();
        if (id != null) {
            pricelist.removeIf(p -> !p.getId().equals(id));
        }
        if (itemID != null) {
            pricelist = pricelist.stream().filter(p -> p.getItems().removeIf(m -> !m.getId().equals(itemID))).collect(Collectors.toList());
        }
        return pricelist;
    }

    @Override
    public Pricelist getOne(Long id) {
        return pricelistRepository.getOne(id);
    }

    @Override
    public Pricelist create(Pricelist pricelist) throws Exception {
        PharmacyAdmin admin = pharmacyAdminService.findByUsername(authenticationService.getUsername());
        if (admin == null) {
            throw new Exception("Administrator apoteke nije ulogovan!");
        }
        Pricelist p;
        if (admin.getPharmacy().getPricelist() != null) {
            p = admin.getPharmacy().getPricelist();
            if (pricelist.getConsultationPrice() != null) {
                p.setConsultationPrice(pricelist.getConsultationPrice());
            }
            if (pricelist.getExaminationPrice() != null) {
                p.setExaminationPrice(pricelist.getExaminationPrice());
            }
        } else {
            p = pricelist;
        }
        Pricelist pl = pricelistRepository.save(p);
        if (admin.getPharmacy() != null) {
            admin.getPharmacy().setPricelist(pl);
            pharmacyService.update(admin.getPharmacy());
        }
        return pl;
    }

    @Override
    public Pricelist update(Pricelist pricelist) {

        return pricelistRepository.save(pricelist);
    }

    @Override
    public Boolean delete(Long id) {
        pricelistRepository.deleteById(id);
        return true;
    }
}
