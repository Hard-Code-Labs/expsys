package ec.com.expensys.web.exception;

import ec.com.expensys.web.utils.MessageCode;
import org.springframework.http.HttpStatus;

public class ExpiredTokenException extends CustomException {

    public ExpiredTokenException(String customMessage, String className) {
        super(MessageCode.TOKEN_EXPIRED.getCode(),
                customMessage,
                className,
                HttpStatus.UNAUTHORIZED,
                false);
    }
}
