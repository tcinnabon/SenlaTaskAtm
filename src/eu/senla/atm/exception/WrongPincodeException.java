package eu.senla.atm.exception;

public class WrongPincodeException extends RuntimeException {
    public WrongPincodeException() {
        super("неверный пинкод");
    }
}
