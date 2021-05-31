package rs.apoteka.exception;

public class InvalidComplaintException extends Exception {
    public InvalidComplaintException() {
        super("Došlo je do greške pri podnošenju žalbe.");
    }

    public InvalidComplaintException(String message) {
        super(message);
    }
}
