package rs.apoteka.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rs.apoteka.auth.JWTProvider;
import rs.apoteka.auth.JWTResponse;
import rs.apoteka.entity.auth.*;
import rs.apoteka.repository.RoleRepository;
import rs.apoteka.repository.UserRepository;
import rs.apoteka.service.intf.UserService;
import rs.apoteka.service.intf.VerificationTokenService;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    VerificationTokenService verificationTokenService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JWTProvider provider;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findValidatedAndEnabled() {
        return userRepository.findByValidatedTrueAndEnabledTrue();
    }

    @Override
    public User getOne(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User create(RegistrationRequest request) throws Exception {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new Exception("Korisnik sa ovom email adresom već postoji!");
        }
        return userRepository.save(new User(request));
    }

    @Override
    public Boolean confirm(String token) throws Exception {
        VerificationToken verificationToken = verificationTokenService.findByToken(token);
        if (verificationToken == null) {
            throw new Exception("Token ne postoji!");
        }
        if (verificationToken.getUser().getValidated()) {
            throw new Exception("Korisnik je već potvrdio registraciju!");
        }
        Calendar calendar = Calendar.getInstance();
        if (verificationToken.getExpiryDate().before(calendar.getTime())) {
            throw new Exception("Token je istekao.");
        }
        User user = findByUsername(verificationToken.getUser().getUsername());
        if (user == null) {
            throw new Exception("Korisnik sa email adresom " + verificationToken.getUser().getUsername() + " ne postoji!");
        }
        user.setValidated(true);
        User validated = userRepository.save(user);
        return validated.getValidated();
    }

    @Override
    public JWTResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = provider.generateToken(authentication);
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        if (!valid(user)) {
            return null;
        }
        return new JWTResponse(token, user.getUsername(), user.getAuthorities());
    }

    @Override
    public User update(User user) throws Exception {
        User updated = getOne(user.getId());
        if (updated == null) {
            throw new Exception("Korisnik nije pronađen!");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        updated.setAddress(user.getAddress());
        updated.setCity(user.getCity());
        updated.setCountry(user.getCountry());
        updated.setForename(user.getForename());
        updated.setSurname(user.getSurname());
        updated.setPhone(user.getPhone());
        updated.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(updated);
    }

    @Override
    public Boolean delete(Long id) {
        User user = getOne(id);
        if (user == null || validatedAndEnabled(user)) {
            return false;
        }
        user.setEnabled(false);
        return !user.getEnabled();
    }

    private Boolean validatedAndEnabled(User user) {
        return user.getValidated() && user.getEnabled();
    }

    private Boolean valid(UserDetailsImpl user) {
        return user.isAccountNonLocked();
    }
}
