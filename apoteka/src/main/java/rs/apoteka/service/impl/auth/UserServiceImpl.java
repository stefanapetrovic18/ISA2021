package rs.apoteka.service.impl.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rs.apoteka.auth.JWTProvider;
import rs.apoteka.auth.JWTResponse;
import rs.apoteka.entity.auth.*;
import rs.apoteka.entity.business.Reservation;
import rs.apoteka.entity.user.*;
import rs.apoteka.exception.UserNotFoundException;
import rs.apoteka.repository.auth.RoleRepository;
import rs.apoteka.repository.auth.UserRepository;
import rs.apoteka.service.intf.auth.AuthenticationService;
import rs.apoteka.service.intf.auth.UserService;
import rs.apoteka.service.intf.auth.VerificationTokenService;
import rs.apoteka.service.intf.business.ReservationService;
import rs.apoteka.service.intf.user.*;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.zip.DataFormatException;

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
    AuthenticationService authenticationService;
    @Autowired
    JWTProvider provider;

    @Autowired
    DermatologistService dermatologistService;
    @Autowired
    PatientService patientService;
    @Autowired
    PharmacistService pharmacistService;
    @Autowired
    PharmacyAdminService pharmacyAdminService;
    @Autowired
    SupplierService supplierService;
    @Autowired
    SystemAdminService systemAdminService;
    @Autowired
    ReservationService reservationService;

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
    public List<User> getUsersWithoutType() {
        List<User> users = findValidatedAndEnabled();
        users.forEach(user -> System.out.println(user.getUsername() + ": " + user.getRoles()));
        users.removeIf(user -> (user.getRoles() != null && !user.getRoles().isEmpty()));
        users.forEach(user -> System.out.println(user.getUsername() + ": " + user.getRoles()));
        return users;
    }

    @Override
    public User create(RegistrationRequest request) throws Exception {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new Exception("Korisnik sa ovom email adresom već postoji!");
        }
        User user;
        if (request.getType().equalsIgnoreCase("patient")) {
            Patient patient = new Patient(request);
            user = patientService.create(patient);
        } else if (request.getType().equalsIgnoreCase("dermatologist")) {
            Dermatologist dermatologist = new Dermatologist(request);
            user = dermatologistService.create(dermatologist);
        } else if (request.getType().equalsIgnoreCase("pharmacist")) {
            Pharmacist pharmacist = new Pharmacist(request);
            user = pharmacistService.create(pharmacist);
        } else if (request.getType().equalsIgnoreCase("pharmacy-admin")) {
            PharmacyAdmin pharmacyAdmin = new PharmacyAdmin(request);
            user = pharmacyAdminService.create(pharmacyAdmin);
        } else if (request.getType().equalsIgnoreCase("supplier")) {
            Supplier supplier = new Supplier(request);
            user = supplierService.create(supplier);
        } else if (request.getType().equalsIgnoreCase("system-admin")) {
            SystemAdmin systemAdmin = new SystemAdmin(request);
            user = systemAdminService.create(systemAdmin);
        } else {
            throw new Exception("Tip korisnika nije definisan, ili ne postoji.");
        }
        return user;
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
        try {
            penaltyCheck(user);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        return new JWTResponse(token, user.getUsername(), user.getAuthorities());
    }

    // Provera rezervacije leka koji nije preuzet i dodela penala.
    private void penaltyCheck(UserDetails userDetails) throws UserNotFoundException {
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PATIENT"))) {
            Patient patient = patientService.findByUsername(userDetails.getUsername());
            if (patient == null) {
                throw new UserNotFoundException();
            }
            List<Reservation> reservations = reservationService.findAllParametrized(null, null, null,
                    null, null,null, patient.getId(),
                    false, null, true);
            if (reservations != null && !reservations.isEmpty()) {
                reservations.removeIf(Reservation::getPenalized);
                patient.setPoints(patient.getPoints() + reservations.size());
                patientService.update(patient);
                reservations.forEach(reservation -> {
                    reservation.setPenalized(true);
                    reservationService.update(reservation);
                });
            }
        }
    }

    @Override
    public String changePassword(PasswordChangeRequest request) throws Exception {
        if (!request.getPassword().equals(request.getRepeatPassword())) {
            return null;
        }
        User user = findByUsername(authenticationService.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        user.setPasswordChanged(true);
        User updated = update(user);
        return "Uspešna promena šifre!";
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
        if (user == null) {
            return false;
        }
        userRepository.delete(user);
        return true;
    }

    private Boolean validatedAndEnabled(User user) {
        return user.getValidated() && user.getEnabled();
    }

    private Boolean valid(UserDetailsImpl user) {
        return user.isAccountNonExpired();
    }
}
