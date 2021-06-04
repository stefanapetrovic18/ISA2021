package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import rs.apoteka.dto.ComplaintResponse;
import rs.apoteka.entity.auth.User;
import rs.apoteka.entity.business.Complaint;
import rs.apoteka.entity.business.Pharmacy;
import rs.apoteka.entity.user.Dermatologist;
import rs.apoteka.entity.user.Patient;
import rs.apoteka.entity.user.Pharmacist;
import rs.apoteka.exception.AuthMismatchException;
import rs.apoteka.exception.ComplaintResolvedException;
import rs.apoteka.exception.InvalidComplaintException;
import rs.apoteka.exception.UserNotFoundException;
import rs.apoteka.repository.business.ComplaintRepository;
import rs.apoteka.service.intf.auth.AuthenticationService;
import rs.apoteka.service.intf.business.ComplaintService;
import rs.apoteka.service.intf.business.PharmacyService;
import rs.apoteka.service.intf.business.RatingService;
import rs.apoteka.service.intf.user.DermatologistService;
import rs.apoteka.service.intf.user.PatientService;
import rs.apoteka.service.intf.user.PharmacistService;
import rs.apoteka.service.intf.user.SystemAdminService;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComplaintServiceImpl implements ComplaintService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private ComplaintRepository complaintRepository;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private DermatologistService dermatologistService;
    @Autowired
    private PharmacistService pharmacistService;
    @Autowired
    private PharmacyService pharmacyService;
    @Autowired
    private SystemAdminService systemAdminService;

    @Override
    public List<Complaint> findAll() {
        return complaintRepository.findAll();
    }

    @Override
    public List<Complaint> findAllParametrized(Long id, Long patientID, Boolean resolved) {
        List<Complaint> complaints = findAll();
        if (id != null) {
            complaints.removeIf(c -> !c.getId().equals(id));
        }
        if (patientID != null) {
            complaints.removeIf(c -> !c.getPatient().getId().equals(patientID));
        }
        if (resolved != null) {
            complaints.removeIf(c -> !c.getResolved().equals(resolved));
        }
        return complaints;
    }

    @Override
    public Complaint getOne(Long id) {
        return complaintRepository.getOne(id);
    }

    @Override
    public Complaint create(Complaint complaint) {
        return complaintRepository.save(complaint);
    }

    @Override
    public Complaint submitPharmacist(Complaint complaint, Long pharmacistID) throws InvalidComplaintException, UserNotFoundException {
        Patient patient = getPatient();
        if (patient == null) {
            throw new UserNotFoundException();
        }
        complaint.setPatient(patient);
        complaint.setResolved(false);
        Pharmacist pharmacist = pharmacistService.getOne(pharmacistID);
        if (pharmacist == null) {
            throw new EntityNotFoundException();
        }
        if (!ratingService.hadConsultationAtPharmacist(patient, pharmacist)) {
            throw new InvalidComplaintException("Korisnik nije imao konsultaciju kod farmaceuta.");
        }
        complaint.setText(
                complaint.getText() + "\n\n" +
                        "ID farmaceuta: " + pharmacist.getId() + "\n\n" +
                        "ID pacijenta: " + patient.getId() + "\n\n" +
                        "Datum podnošenja: " + LocalDateTime.now().toString()
        );
        return create(complaint);
    }

    @Override
    public Complaint submitDermatologist(Complaint complaint, Long dermatologistID) throws InvalidComplaintException, UserNotFoundException {
        Patient patient = getPatient();
        if (patient == null) {
            throw new UserNotFoundException();
        }
        complaint.setPatient(patient);
        complaint.setResolved(false);
        Dermatologist dermatologist = dermatologistService.getOne(dermatologistID);
        if (dermatologist == null) {
            throw new EntityNotFoundException();
        }
        if (!ratingService.hadExaminationAtDermatologist(patient, dermatologist)) {
            throw new InvalidComplaintException("Korisnik nije imao pregled kod dermatologa.");
        }
        complaint.setText(
                complaint.getText() + "\n\n" +
                        "ID dermatologa: " + dermatologist.getId() + "\n\n" +
                        "ID pacijenta: " + patient.getId() + "\n\n" +
                        "Datum podnošenja: " + LocalDateTime.now().toString()
        );
        return create(complaint);
    }

    @Override
    public Complaint submitPharmacy(Complaint complaint, Long pharmacyID) throws InvalidComplaintException, UserNotFoundException {
        Patient patient = getPatient();
        if (patient == null) {
            throw new UserNotFoundException();
        }
        complaint.setPatient(patient);
        complaint.setResolved(false);
        Pharmacy pharmacy = pharmacyService.getOne(pharmacyID);
        if (pharmacy == null) {
            throw new EntityNotFoundException();
        }
        if (!ratingService.usedPharmacy(patient, pharmacy)) {
            throw new InvalidComplaintException("Korisnik nije koristio usluge apoteke.");
        }
        complaint.setText(
                complaint.getText() + "\n\n" +
                        "ID apoteke: " + pharmacy.getId() + "\n\n" +
                        "ID pacijenta: " + patient.getId() + "\n\n" +
                        "Datum podnošenja: " + LocalDateTime.now().toString()
        );
        return create(complaint);
    }

    @Override
    public void answer(ComplaintResponse response) throws ComplaintResolvedException, UserNotFoundException, AuthMismatchException {
        Complaint complaint = getOne(response.getComplaintID());
        if (complaint == null) {
            throw new EntityNotFoundException("Žalba nije pronađena.");
        }
        if (complaint.getResolved()) {
            throw new ComplaintResolvedException();
        }
        sendEmail(complaint.getPatient(), complaint.getId(), complaint.getText(), response.getText());
        complaint.setResolved(true);
        update(complaint);
    }

    private void sendEmail(User user, Long id, String complaint, String text) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getUsername());
        email.setSubject("Odgovor na žalbu ID=" + id);
        email.setText("Poštovani/a " + user.getForename() + ",\n\n" +
                "Nedavno ste uložili žalbu na rad jednog od naših farmaceuta, dermatologa ili apoteka.\n\n" +
                "Ispod se nalazi pun tekst vaše žalbe.\n\n" +
                "\"" + complaint + "\"" + "\n\n" +
                "Hvala vam na povratnim informacijama.\n\n" +
                "U nastavku, šaljemo vam pun odgovor na vašu žalbu.\n\n" +
                "\"" + text + "\"" + "\n\n" +
                "Srdačan pozdrav,\n\n" +
                "ISA");
        mailSender.send(email);
    }

    private Patient getPatient() {
        return patientService.findByUsername(authenticationService.getUsername());
    }

    @Override
    public Complaint update(Complaint complaint) throws UserNotFoundException, AuthMismatchException {
        Complaint c = getOne(complaint.getId());
        c.setText(complaint.getText());
        c.setResolved(complaint.getResolved());
        return complaintRepository.save(c);
    }

    @Override
    public Boolean delete(Long id) {
        complaintRepository.deleteById(id);
        return getOne(id) == null;
    }
}
