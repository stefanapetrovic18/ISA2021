package rs.apoteka.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.auth.User;
import rs.apoteka.entity.auth.VerificationToken;
import rs.apoteka.repository.VerificationTokenRepository;
import rs.apoteka.service.intf.UserService;
import rs.apoteka.service.intf.VerificationTokenService;

import java.util.List;
import java.util.UUID;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private UserService userService;
    @Autowired
    JavaMailSender mailSender;
    @Override
    public List<VerificationToken> findAll() {
        return verificationTokenRepository.findAll();
    }

    @Override
    public VerificationToken getOne(Long id) {
        return verificationTokenRepository.getOne(id);
    }

    @Override
    public VerificationToken findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Override
    public VerificationToken create(String username) throws Exception {
        VerificationToken token;
        User user = userService.findByUsername(username);
        if (user == null || user.getValidated() || !user.getEnabled()) {
            throw new Exception("Korisnik ne postoji, već je validiran ili je profil neaktivan.");
        }
        VerificationToken existing = verificationTokenRepository.findByUser(user);
        String uuid = UUID.randomUUID().toString();
        if (existing != null) {
            token = existing;
            token.setToken(uuid);
        } else {
            token = new VerificationToken(uuid, user);
        }
        VerificationToken saved = verificationTokenRepository.save(token);
        sendConfirmationEmail(saved.getUser());
        return saved;
    }


    private void sendConfirmationEmail(User user) throws Exception {
        VerificationToken token = findByUser(user);
        if (token == null) {
            token = create(user);
        }
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getUsername());
        email.setSubject("Potvrda registracije");
        email.setText("Postovani/a " + user.getForename() + ",\n\n" +
                "Hvala vam što ste odabrali baš nas.\n\n" +
                "Da biste potvrdili vašu registraciju, molimo vas da kliknete na link ispod.\n\n" +
                "http://localhost:8080/api/user/confirm?token=" + token.getToken() + "\n\n" +
                "Link važi 24 sata od registracije." +
                "Ukoliko vam je istekao, molimo vas da kliknete na link ispod kako biste dobili novi link.\n\n" +
                "http://localhost:8080/api/user/request-token?email=" + user.getUsername() + "\n\n" +
                "Srdačan pozdrav,\n\n" +
                "ISA");
        mailSender.send(email);
    }

    @Override
    public void requestToken(String username) throws Exception {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new Exception("Korisnik ne postoji!");
        }
        VerificationToken token = findByUser(user);
        if (token == null) {
            create(user);
        }
        String uuid = UUID.randomUUID().toString();
        token.setToken(uuid);
        VerificationToken updated = verificationTokenRepository.save(token);
        sendConfirmationEmail(updated.getUser());
    }

    @Override
    public VerificationToken findByUser(User user) {
        return verificationTokenRepository.findByUser(user);
    }

    @Override
    public VerificationToken create(User user) throws Exception {
        VerificationToken token;
        if (user == null || user.getValidated() || !user.getEnabled()) {
            throw new Exception("Korisnik ne postoji, već je validiran ili je profil neaktivan.");
        }
        VerificationToken existing = verificationTokenRepository.findByUser(user);
        String uuid = UUID.randomUUID().toString();
        if (existing != null) {
            token = existing;
            token.setToken(uuid);
        } else {
            token = new VerificationToken(uuid, user);
        }
        VerificationToken saved = verificationTokenRepository.save(token);
        sendConfirmationEmail(saved.getUser());
        return saved;
    }

    @Override
    public VerificationToken update(VerificationToken token) throws Exception {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        verificationTokenRepository.deleteById(id);
        return true;
    }
}
