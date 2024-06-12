package ec.com.expensys.web.exception;

public final class MailAlreadyExistException extends RuntimeException{

    public MailAlreadyExistException() {
        super();
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
