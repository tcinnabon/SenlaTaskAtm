package eu.senla.atm.exception;

public class NotEnoughMoneyAtmException extends RuntimeException {
    public NotEnoughMoneyAtmException() {
        super("Не хватает средств в банкомате");
    }
}
