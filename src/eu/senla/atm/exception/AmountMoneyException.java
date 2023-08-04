package eu.senla.atm.exception;

public class AmountMoneyException extends RuntimeException {
    public AmountMoneyException() {
        super("Денежная сумма вносимая на счёт должна быть меньше 1000000 и больше 0");
    }
}
