package rs.apoteka.exception;

public class InvalidRatingException extends Exception {
    public InvalidRatingException() {
        super("Došlo je do greške pri ocenjivanju.");
    }

    public InvalidRatingException(String message) {
        super(message);
    }
}
