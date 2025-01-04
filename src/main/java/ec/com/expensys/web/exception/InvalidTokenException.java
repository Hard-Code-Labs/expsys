package ec.com.expensys.web.exception;

import ec.com.expensys.web.utils.MessageCode;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends CustomException{
    public InvalidTokenException(String customMessage, String className, boolean sendToLog,String messageToLog ) {
        super(MessageCode.TOKEN_INVALID.getCode(), customMessage, className, HttpStatus.BAD_REQUEST, sendToLog, messageToLog);
    }
}
