package eu.senla.atm.exception;

public class IncorrectPinException extends RuntimeException {
    public IncorrectPinException() {
        super("некорректный пинкод");
    }
}
