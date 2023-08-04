package eu.senla.atm.exception;

public class NotAuthorizedException extends RuntimeException{
    public NotAuthorizedException() {
        super("авторизация завершилась с ошибкой");
    }
}
