package rs.apoteka.exception;

public class PasswordNotChangedException extends Exception {
    public PasswordNotChangedException() {
        super("Korisnik nije promenio lozinku!");
    }
}
