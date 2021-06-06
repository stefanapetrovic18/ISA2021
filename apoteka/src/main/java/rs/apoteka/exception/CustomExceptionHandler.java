package rs.apoteka.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import rs.apoteka.auth.JWTResponse;

// Klasa koja hendluje greske na nivou kontrolera.
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    // Ukoliko je korisniku onemogucen pristup,
    // redirektuj ga na stranicu koja prikazuje informaciju o gresci.
    @ExceptionHandler(UserNotEnabledException.class)
    public final ResponseEntity<JWTResponse> handleUserNotEnabledException() {
        JWTResponse response = new JWTResponse();
        response.setRedirectURL("forbidden-access");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    // Ukoliko je korisniku onemogucen pristup,
    // redirektuj ga na stranicu koja prikazuje informaciju o gresci.
    @ExceptionHandler(PatientPenalizedException.class)
    public final ResponseEntity<JWTResponse> handlePatientPenalizedException() {
        JWTResponse response = new JWTResponse();
        response.setRedirectURL("penalized");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    // Ukoliko korisnik nije validirao registraciju,
    // redirektuj ga na stranicu koja prikazuje informaciju o gresci.
    @ExceptionHandler(UserNotValidatedException.class)
    public final ResponseEntity<JWTResponse> handleUserNotValidatedException() {
        JWTResponse response = new JWTResponse();
        response.setRedirectURL("validate");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    // Ukoliko korisnik nije prvi put promenio lozinku,
    // redirektuj ga na stranicu na kojoj se menja lozinka.
    @ExceptionHandler(PasswordNotChangedException.class)
    public final ResponseEntity<JWTResponse> handlePasswordNotChangedException() {
        JWTResponse response = new JWTResponse();
        response.setRedirectURL("change-password");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
