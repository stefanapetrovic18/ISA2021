package rs.apoteka.controller.auth;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.auth.JWTResponse;
import rs.apoteka.entity.auth.LoginRequest;
import rs.apoteka.entity.auth.PasswordChangeRequest;
import rs.apoteka.entity.auth.RegistrationRequest;
import rs.apoteka.entity.auth.User;
import rs.apoteka.service.intf.auth.UserService;
import rs.apoteka.service.intf.auth.VerificationTokenService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    private static final Gson gson = new Gson();
    @Autowired
    UserService userService;
    @Autowired
    VerificationTokenService verificationTokenService;

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) throws Exception {
        User user = userService.create(request);
        if (user == null) {
            return new ResponseEntity<>(gson.toJson("Neuspešna registracija!"), HttpStatus.BAD_REQUEST);
        }
        verificationTokenService.create(user);
        return new ResponseEntity<>(gson.toJson("Registracija uspešna! Proverite vaš email za potvrdu registracije."), HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) throws Exception {
        JWTResponse response = userService.login(request);
        if (response == null) {
            return new ResponseEntity<>(gson.toJson("Neuspešno prijavljivanje!"), HttpStatus.BAD_REQUEST);
        }
        if (!userService.findByUsername(request.getUsername()).getPasswordChanged()) {
            response.setRedirectURL("change-password");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest request) throws Exception {
        String response = userService.changePassword(request);
        if (response == null) {
            return new ResponseEntity<>(gson.toJson("Neuspešna promena šifre!"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(gson.toJson(response), HttpStatus.OK);
    }

    @GetMapping(value = "/confirm")
    public ResponseEntity<String> confirm(@RequestParam String token) throws Exception {
        Boolean confirm = userService.confirm(token);
        if (!confirm) {
            return new ResponseEntity<>(gson.toJson("Došlo je do greške."), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(gson.toJson("Registracija je potvrđena."), HttpStatus.OK);
    }

    @GetMapping(value = "/request-token")
    public ResponseEntity<?> requestToken(@RequestParam String email) throws Exception {
        verificationTokenService.requestToken(email);
        return new ResponseEntity<>(gson.toJson("Zahtev je poslat. Molimo vas da proverite email kako biste potvrdili registraciju."), HttpStatus.OK);
    }

    @GetMapping(value = "/get/without-type")
    public ResponseEntity<List<User>> getUsersWithoutType() {
        return new ResponseEntity<>(userService.getUsersWithoutType(), HttpStatus.OK);
    }

}
