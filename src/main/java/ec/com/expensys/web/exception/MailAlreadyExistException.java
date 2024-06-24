package ec.com.expensys.web.exception;

public final class MailAlreadyExistException extends RuntimeException{

    private static final String defaultMessage = "Mail already exist";

    public MailAlreadyExistException() {
        super(defaultMessage);
    }

    public MailAlreadyExistException(String message) {
        super(message);
    }

    public MailAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
