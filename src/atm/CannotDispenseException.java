package atm;

public class CannotDispenseException extends Exception {
    public CannotDispenseException(String message) {
        super(message);
    }
}
