package ec.com.expensys.web.exception;

import org.springframework.http.HttpStatus;

public class DecryptException extends CustomException {

    public DecryptException(int code, String message, String className, boolean sendToLog, String logMessage) {
        super(code, message, className, HttpStatus.CONFLICT, sendToLog, logMessage);
    }
}
