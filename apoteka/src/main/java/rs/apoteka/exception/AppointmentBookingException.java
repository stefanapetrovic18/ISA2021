package rs.apoteka.exception;

public class AppointmentBookingException extends Exception {
    public AppointmentBookingException() {
        super("Termin nije slobodan.");
    }
}
