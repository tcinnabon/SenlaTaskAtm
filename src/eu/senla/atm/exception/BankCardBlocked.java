package eu.senla.atm.exception;

public class BankCardBlocked extends RuntimeException {
    public BankCardBlocked() {
        super("карта заблокирована");
    }
}
