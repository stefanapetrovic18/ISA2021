package rs.apoteka.exception;

public class UserNotEnabledException extends Exception {
    public UserNotEnabledException() {
        super("Korisniku je uskraćeno pravo pristupa.");
    }
}
