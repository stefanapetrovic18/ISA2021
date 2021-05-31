package rs.apoteka.exception;

public class AppointmentBookedException extends Exception {
    public AppointmentBookedException() {
        super("Termin nije slobodan.");
    }
}
