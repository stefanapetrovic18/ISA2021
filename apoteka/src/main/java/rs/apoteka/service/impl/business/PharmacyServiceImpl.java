package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.dto.*;
import rs.apoteka.entity.auth.User;
import rs.apoteka.entity.business.*;
import rs.apoteka.entity.user.Dermatologist;
import rs.apoteka.entity.user.Patient;
import rs.apoteka.entity.user.Pharmacist;
import rs.apoteka.entity.user.PharmacyAdmin;
import rs.apoteka.exception.UserNotFoundException;
import rs.apoteka.repository.business.PharmacyRepository;
import rs.apoteka.service.intf.auth.AuthenticationService;
import rs.apoteka.service.intf.business.*;
import rs.apoteka.service.intf.user.DermatologistService;
import rs.apoteka.service.intf.user.PatientService;
import rs.apoteka.service.intf.user.PharmacistService;
import rs.apoteka.service.intf.user.PharmacyAdminService;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class PharmacyServiceImpl implements PharmacyService {
    @Autowired
    private PharmacyRepository pharmacyRepository;
    @Autowired
    private PharmacistService pharmacistService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private DermatologistService dermatologistService;
    @Autowired
    private PharmacyAdminService pharmacyAdminService;
    @Autowired
    private PricelistService pricelistService;
    @Autowired
    private ExaminationService examinationService;
    @Autowired
    private ConsultationService consultationService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private StockpileService stockpileService;
    @Autowired
    private ItemService itemService;

    @Override
    public List<Pharmacy> findAll() {
        return pharmacyRepository.findAll();
    }

    @Override
    public Pharmacy getOne(Long id) {
        return pharmacyRepository.getOne(id);
    }

    @Override
    public List<Pharmacy> findAllParametrized(Long id, Long pharmacistID, Long dermatologistID, Long consultationID,
                                              Long adminID, Long examinationID, Long promotionID, Long userID,
                                              Long pricelistID, Double rating, Double ratingFrom, Double ratingTo) {
        List<Pharmacy> pharmacies = findAll();
        if (id != null) {
            pharmacies.removeIf(p -> !p.getId().equals(id));
        }
        if (pharmacistID != null) {
            pharmacies = pharmacies.stream().filter(p -> p.getPharmacists().removeIf(ph -> !ph.getId().equals(pharmacistID))).collect(Collectors.toList());
        }
        if (dermatologistID != null) {
            pharmacies = pharmacies.stream().filter(p -> p.getDermatologists().removeIf(ph -> !ph.getId().equals(dermatologistID))).collect(Collectors.toList());
        }
        if (consultationID != null) {
            pharmacies = pharmacies.stream().filter(p -> p.getConsultations().removeIf(ph -> !ph.getId().equals(consultationID))).collect(Collectors.toList());
        }
        if (adminID != null) {
            pharmacies = pharmacies.stream().filter(p -> p.getAdmins().removeIf(ph -> !ph.getId().equals(adminID))).collect(Collectors.toList());
        }
        if (examinationID != null) {
            pharmacies = pharmacies.stream().filter(p -> p.getExaminations().removeIf(ph -> !ph.getId().equals(examinationID))).collect(Collectors.toList());
        }
        if (promotionID != null) {
            pharmacies = pharmacies.stream().filter(p -> p.getPromotions().removeIf(ph -> !ph.getId().equals(promotionID))).collect(Collectors.toList());
        }
        if (userID != null) {
            pharmacies = pharmacies.stream().filter(p -> p.getSubscriptions().removeIf(ph -> !ph.getId().equals(userID))).collect(Collectors.toList());
        }
        if (pricelistID != null) {
            pharmacies.removeIf(p -> !p.getPricelist().getId().equals(pricelistID));
        }
        if (rating != null) {
            pharmacies.removeIf(p -> !p.getRating().equals(rating));
        }
        if (ratingFrom != null) {
            pharmacies.removeIf(p -> p.getRating() < ratingFrom);
        }
        if (ratingTo != null) {
            pharmacies.removeIf(p -> p.getRating() > ratingTo);
        }
        return pharmacies;
    }

    @Override
    public List<Pharmacy> findSubs() {
        User user = authenticationService.getUser();
        List<Pharmacy> pharmacies = new ArrayList<>();
        for (Pharmacy p :
                findAll()) {
            for (Patient u :
                    p.getSubscriptions()) {
                if (u.getId().equals(user.getId())) {
                    pharmacies.add(p);
                }
            }
        }
        return pharmacies;
    }

    @Override
    public List<Pharmacy> findAllContainingMedicine(Long medicineID) {
        List<Pharmacy> pharmacies = new ArrayList<>();
        for (Pharmacy p :
                findAll()) {
            for (Item i : p.getPricelist().getItems()) {
                if (i.getMedicine().getId().equals(medicineID)) {
                    pharmacies.add(p);
                }
            }
        }
        return pharmacies;
    }

    @Override
    public List<Pharmacy> findAllByPharmacistFreeAt(LocalDateTime localDateTime) {
        List<Pharmacy> pharmacies = new ArrayList<>();
        for (Pharmacy p : findAll()) {
            for (Pharmacist ph : p.getPharmacists()) {
                for (Consultation c : ph.getConsultations()) {
                    if (c.getConsultationDate().plusMinutes(c.getDuration()).isBefore(localDateTime)
                            ||
                            c.getConsultationDate().isAfter(localDateTime)) {
                        pharmacies.add(p);
                    }
                }
            }
        }
        return pharmacies;
    }

    @Override
    public Boolean subscribe(Long id) throws Exception {
        Patient patient = patientService.findByUsername(authenticationService.getUsername());
        if (patient == null) {
            throw new Exception("Pacijent nije ulogovan!");
        }
        Pharmacy pharmacy = getOne(id);
        if (pharmacy == null) {
            throw new Exception("Apoteka ne postoji!");
        }
        AtomicReference<Boolean> subscribed = new AtomicReference<>(false);
        patient.getSubscriptions().forEach(p -> {
            if (p.getId().equals(id)) {
                subscribed.set(true);
            }
        });
        pharmacy.getSubscriptions().forEach(p -> {
            if (p.getId().equals(patient.getId())) {
                subscribed.set(true);
            }
        });
        if (subscribed.get()) {
            throw new Exception("Već ste pretplaćeni!");
        }
        patient.getSubscriptions().add(pharmacy);
        pharmacy.getSubscriptions().add(patient);
        patientService.update(patient);
        update(pharmacy);
        return true;
    }

    @Override
    public Boolean unsubscribe(Long id) throws Exception {
        Patient patient = patientService.findByUsername(authenticationService.getUsername());
        if (patient == null) {
            throw new Exception("Pacijent nije ulogovan!");
        }
        Pharmacy pharmacy = getOne(id);
        if (pharmacy == null) {
            throw new Exception("Apoteka ne postoji!");
        }
        patient.getSubscriptions().removeIf(p -> p.getId().equals(pharmacy.getId()));
        pharmacy.getSubscriptions().removeIf(p -> p.getId().equals(patient.getId()));
        patientService.update(patient);
        update(pharmacy);
        return true;
    }

    @Override
    public BusinessReport getBusinessReport(LocalDate profitFrom, LocalDate profitUntil, Integer year) throws UserNotFoundException {
        PharmacyAdmin admin = pharmacyAdminService.findByUsername(authenticationService.getUsername());
        if (admin == null) {
            throw new UserNotFoundException();
        }
        Pharmacy pharmacy = getOne(admin.getPharmacy().getId());
        if (pharmacy == null) {
            throw new EntityNotFoundException();
        }
        // Izvestaj o poslovanju.
        BusinessReport businessReport = new BusinessReport();
        businessReport.setPharmacyName(pharmacy.getName());

        // Izvestaj o prosecnim ocenama.
        RatingReport ratingReport = new RatingReport();
        ratingReport.setPharmacyRating(pharmacy.getRating());
        // Ocene dermatologa.
        List<DermatologistRating> dermatologistRatings = new ArrayList<>();
        List<Dermatologist> dermatologists = dermatologistService.findAllByPharmaciesContaining(pharmacy.getId());
        if (dermatologists != null && !dermatologists.isEmpty()) {
            dermatologists.forEach(d -> {
                DermatologistRating dermatologistRating = new DermatologistRating();
                dermatologistRating.setDermatologistName(d.getForename() + " " + d.getSurname());
                dermatologistRating.setDermatologistRating(d.getRating());
                dermatologistRatings.add(dermatologistRating);
            });
        }
        ratingReport.setDermatologistRatings(dermatologistRatings);
        // Ocene farmaceuta.
        List<PharmacistRating> pharmacistRatings = new ArrayList<>();
        List<Pharmacist> pharmacists = pharmacistService.findAllParametrized(null, null, pharmacy.getId(),
                null, null, null, null, null, null);
        if (pharmacists != null && !pharmacists.isEmpty()) {
            pharmacists.forEach(p -> {
                PharmacistRating pharmacistRating = new PharmacistRating();
                pharmacistRating.setPharmacistName(p.getForename() + " " + p.getSurname());
                pharmacistRating.setPharmacistRating(p.getRating());
                pharmacistRatings.add(pharmacistRating);
            });
        }
        ratingReport.setPharmacistRatings(pharmacistRatings);
        businessReport.setRatingReport(ratingReport);

        // Izvestaj o prihodu.
        IncomeReport incomeReport = new IncomeReport();
        incomeReport.setReportFrom(profitFrom);
        incomeReport.setReportUntil(profitUntil);
        incomeReport.setIncome(calculateIncome(pharmacy, profitFrom, profitUntil));
        businessReport.setIncomeReport(incomeReport);

        // Izvestaj o prodaji lekova.
        businessReport.setMonthlyMedicineSales(getMedicineSpendingDuringYear(pharmacy.getId(), year));
        // Izvestaj o odrzanim pregledima.
        businessReport.setMonthlyExaminations(getExaminationsDuringYear(pharmacy.getId(), year));
        // Izvestaj o odrzanim konsultacijama.
        businessReport.setMonthlyConsultations(getConsultationsDuringYear(pharmacy.getId(), year));

        return businessReport;
    }

    private List<Integer> getConsultationsDuringYear(Long pharmacyID, Integer year) {
        List<Integer> spending = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            spending.add(getConsultationsDuringMonth(pharmacyID, i+1, year));
        }
        return spending;
    }

    private Integer getConsultationsDuringMonth(Long pharmacyID, Integer month, Integer year) {
        Integer spending = 0;
        List<Consultation> consultations = new ArrayList<>();
        if (month != 12) {
            consultations = consultationService.findAllByPharmacyIDAndDateRange(pharmacyID,
                    LocalDate.of(year, month, 1),
                    LocalDate.of(year, month + 1, 1));
        } else {
            consultations = consultationService.findAllByPharmacyIDAndDateRange(pharmacyID,
                    LocalDate.of(year, month, 1),
                    LocalDate.of(year + 1, 1, 1));
        }
        if (consultations != null && !consultations.isEmpty()) {
            spending += consultations.size();
        }
        return spending;
    }

    private List<Integer> getExaminationsDuringYear(Long pharmacyID, Integer year) {
        List<Integer> spending = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            spending.add(getExaminationsDuringMonth(pharmacyID, i+1, year));
        }
        return spending;
    }

    private Integer getExaminationsDuringMonth(Long pharmacyID, Integer month, Integer year) {
        Integer spending = 0;
        List<Examination> examinations = new ArrayList<>();
        if (month != 12) {
            examinations = examinationService.findAllByPharmacyIDAndDateRange(pharmacyID,
                    LocalDate.of(year, month, 1),
                    LocalDate.of(year, month + 1, 1));
        } else {
            examinations = examinationService.findAllByPharmacyIDAndDateRange(pharmacyID,
                    LocalDate.of(year, month, 1),
                    LocalDate.of(year + 1, 1, 1));
        }
        if (examinations != null && !examinations.isEmpty()) {
            spending += examinations.size();
        }
        return spending;
    }

    private List<Integer> getMedicineSpendingDuringYear(Long pharmacyID, Integer year) {
        List<Integer> spending = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            spending.add(getMedicineSpendingDuringMonth(pharmacyID, i+1, year));
        }
        return spending;
    }

    private Integer getMedicineSpendingDuringMonth(Long pharmacyID, Integer month, Integer year) {
        Integer spending = 0;
        List<Reservation> reservations = new ArrayList<>();
        if (month != 12) {
            reservations = reservationService.findAllByPharmacyIDAndDateRange(pharmacyID,
                    LocalDate.of(year, month, 1),
                    LocalDate.of(year, month + 1, 1));
        } else {
            reservations = reservationService.findAllByPharmacyIDAndDateRange(pharmacyID,
                    LocalDate.of(year, month, 1),
                    LocalDate.of(year + 1, 1, 1));
        }
        if (reservations != null && !reservations.isEmpty()) {
            spending += reservations.size();
        }
        return spending;
    }

    private Double calculateIncome(Pharmacy pharmacy, LocalDate from, LocalDate until) {
        Double income = 0.0;
        Pricelist pricelist = pharmacy.getPricelist();
        List<Examination> examinations = examinationService.findAllByPharmacyIDAndDateRange(pharmacy.getId(), from, until);
        if (examinations != null) {
            income += examinations.size() * pricelist.getExaminationPrice();
        }
        List<Consultation> consultations = consultationService.findAllByPharmacyIDAndDateRange(pharmacy.getId(), from, until);
        if (consultations != null) {
            income += consultations.size() * pricelist.getConsultationPrice();
        }
        List<Reservation> reservations = reservationService.findAllByPharmacyIDAndDateRange(pharmacy.getId(), from, until);
        if (reservations != null && !reservations.isEmpty()) {
            Double medicineIncome = 0.0;
            for (Reservation r: reservations) {
                medicineIncome += itemService.getPriceAtDate(pricelist.getItems(), r.getMedicine().getId(), from, until);
            }
            income += medicineIncome;
        }
        return income;
    }

    @Override
    public Pharmacy create(Pharmacy pharmacy) {
        if (pharmacy.getPharmacists() != null && pharmacy.getPharmacists().size() > 0)
            pharmacy = removeWorkingPharmacists(pharmacy);
        Pharmacy ph = pharmacyRepository.save(pharmacy);
        if (ph.getPharmacists() != null && ph.getPharmacists().size() > 0)
            ph.getPharmacists().forEach(pharmacist -> {
                pharmacist.setPharmacy(ph);
                pharmacistService.update(pharmacist);
            });
        if (ph.getDermatologists() != null && ph.getDermatologists().size() > 0)
            ph.getDermatologists().forEach(dermatologist -> {
                System.out.println(dermatologist);
                if (dermatologist.getPharmacies() != null) {
                    dermatologist.getPharmacies().add(ph);
                } else {
                    dermatologist.setPharmacies(new ArrayList<Pharmacy>() {{
                        add(ph);
                    }});
                }
//                dermatologistService.update(dermatologist);
            });
        if (ph.getConsultations() != null && ph.getConsultations().size() > 0)
            ph.getConsultations().forEach(consultation -> consultation.setPharmacy(ph));
        if (ph.getAdmins() != null && ph.getAdmins().size() > 0) ph.getAdmins().forEach(admin -> admin.setPharmacy(ph));
        if (ph.getExaminations() != null && ph.getExaminations().size() > 0)
            ph.getExaminations().forEach(examination -> examination.setPharmacy(ph));
        if (ph.getPromotions() != null && ph.getPromotions().size() > 0)
            ph.getPromotions().forEach(consultation -> consultation.setPharmacy(ph));
        return ph;
    }

    @Override
    public Pharmacy update(Pharmacy pharmacy) {
        Pharmacy ph = removeWorkingPharmacists(pharmacy);
        return pharmacyRepository.save(ph);
    }

    public Pharmacy removeWorkingPharmacists(Pharmacy pharmacy) {
        for (Pharmacy ph : findAll()) {
            if (!ph.getId().equals(pharmacy.getId())) {
                // Farmaceut je zaposlen u drugoj apoteci.
                // Izbaci ga iz liste zaposlenih u ovoj apoteci.
                pharmacy.getPharmacists().removeIf(pharmacist -> ph.getPharmacists().contains(pharmacist));
            }
        }
        return pharmacy;
    }

    @Override
    public Boolean delete(Long id) {
        pharmacyRepository.deleteById(id);
        return true;
    }
}
