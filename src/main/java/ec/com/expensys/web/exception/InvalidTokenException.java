package ec.com.expensys.web.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends CustomException{
    public InvalidTokenException(String customMessage, String className, boolean sendToLog,String messageToLog ) {
        super(ErrorCode.TOKEN_INVALID.getCode(), customMessage, className, HttpStatus.BAD_REQUEST, sendToLog, messageToLog);
    }
}
