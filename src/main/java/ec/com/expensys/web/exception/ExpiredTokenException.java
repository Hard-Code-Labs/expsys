package ec.com.expensys.web.exception;

import org.springframework.http.HttpStatus;

public class ExpiredTokenException extends CustomException {

    public ExpiredTokenException(String customMessage, String className) {
        super(ErrorCode.TOKEN_EXPIRED.getCode(),
                customMessage,
                className,
                HttpStatus.UNAUTHORIZED,
                false);
    }
}
