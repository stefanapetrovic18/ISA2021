package rs.apoteka.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("Korisnik sa zadatim korisničkim imenom ne postoji!");
    }
}
