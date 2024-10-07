package ec.com.expensys.web.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class CustomException extends RuntimeException {

    private final int code;
    private final String message;
    private final String className;
    private final HttpStatus httpStatus;
    private final boolean sendToLog;
    private final String messageToLog;

    public CustomException(int code, String message, String className, HttpStatus httpStatus,
                           boolean sendToLog, String messageToLog) {
        this.code = code;
        this.message = message;
        this.className = className;
        this.httpStatus = httpStatus;
        this.sendToLog = sendToLog;
        this.messageToLog = messageToLog;
    }

    public CustomException(int code, String message, String className, HttpStatus httpStatus, boolean sendToLog) {
        this.code = code;
        this.message = message;
        this.className = className;
        this.httpStatus = httpStatus;
        this.sendToLog = sendToLog;
        this.messageToLog = "";
    }
}
