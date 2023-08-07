package eu.senla.atm.exception;

public class UpdateFileException extends RuntimeException {
    public UpdateFileException() {
        super("Ошибка обновления файла");
    }
}
