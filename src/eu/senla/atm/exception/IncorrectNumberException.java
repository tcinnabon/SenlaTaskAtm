package eu.senla.atm.exception;

public class IncorrectNumberException extends RuntimeException {
    public IncorrectNumberException() {
        super("некорректный номер карты");
    }
}
