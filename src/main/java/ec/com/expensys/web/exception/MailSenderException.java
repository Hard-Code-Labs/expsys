package ec.com.expensys.web.exception;

import org.springframework.http.HttpStatus;

public class MailSenderException extends CustomException {

    public MailSenderException(int code, String message, String className, String messageToLog) {
        super(code, message, className, HttpStatus.CONFLICT, true, messageToLog);
    }
}
