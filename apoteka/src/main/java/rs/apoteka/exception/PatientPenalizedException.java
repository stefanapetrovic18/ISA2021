package rs.apoteka.exception;

public class PatientPenalizedException extends Exception {
    public PatientPenalizedException() {
        super("Korisnik je rezervisao 3 ili više lekova ovog meseca, a nije ih preuzeo.");
    }
}
