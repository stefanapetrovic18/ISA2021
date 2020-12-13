package rs.apoteka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.apoteka.auth.JWTResponse;
import rs.apoteka.entity.auth.LoginRequest;
import rs.apoteka.entity.auth.RegistrationRequest;
import rs.apoteka.entity.auth.User;
import rs.apoteka.service.intf.UserService;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) throws Exception {
        User user = userService.create(request);
        if (user == null) {
            return new ResponseEntity<>("Neuspešna registracija!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Registracija uspešna! Proverite vaš email za potvrdu registracije.", HttpStatus.OK);
    }
    @GetMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) throws Exception {
        JWTResponse response = userService.login(request);
        if (response == null) {
            return new ResponseEntity<>("Neuspešno prijavljivanje!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
