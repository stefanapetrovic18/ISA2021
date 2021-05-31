package rs.apoteka.exception;

public class AuthMismatchException extends Exception {
    public AuthMismatchException() {
        super("Ulogovani korisnik i korisnik koji pokušava da promeni podatke nisu isti.");
    }
}
