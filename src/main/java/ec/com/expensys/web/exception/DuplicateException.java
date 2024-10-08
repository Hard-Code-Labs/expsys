package ec.com.expensys.web.exception;

import org.springframework.http.HttpStatus;

public class DuplicateException extends CustomException{


    public DuplicateException(int code, String message, String className) {
        super(code, message, className, HttpStatus.CONFLICT, false);
    }
}
