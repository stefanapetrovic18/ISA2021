package rs.apoteka.exception;

public class UserNotValidatedException extends Exception {
    public UserNotValidatedException() {
        super("Korisnik nije validirao svoj profil.");
    }
}
