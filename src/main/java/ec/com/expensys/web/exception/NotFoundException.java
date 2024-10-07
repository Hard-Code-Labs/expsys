package ec.com.expensys.web.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CustomException {

    public NotFoundException(int code, String message, String className, boolean sendToLog) {
        super(code, message, className, HttpStatus.NOT_FOUND, sendToLog);
    }
}
