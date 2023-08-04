package eu.senla.atm.exception;

public class CashWithdrawalException extends RuntimeException {
    public CashWithdrawalException() {
        super("Денежная сумма не может быть меньше нуля или равна ему");
    }
}
