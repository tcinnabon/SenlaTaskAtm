package eu.senla.atm.exception;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException() {
        super("Не хватает средств на счёте");
    }
}
