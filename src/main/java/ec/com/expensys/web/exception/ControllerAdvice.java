package ec.com.expensys.web.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomResponse> handlerException(CustomException ce, HttpServletRequest request) {

        if(ce.isSendToLog()){
            log.error("Error en la clase {} -> {}",ce.getClassName(),ce.getMessage());
        }

        CustomResponse response = CustomResponse.builder()
                .code(ce.getCode())
                .customMessage(ce.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(ce.getHttpStatus())
                .body(response);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handlerValidationException(MethodArgumentNotValidException ve, HttpServletRequest request) {

        Map<String,String> errors = new HashMap<>();
        ve.getBindingResult().getFieldErrors().forEach(error -> {
            String field = error.getField();
            String message = error.getDefaultMessage();
            errors.put(field, message);
        });

        CustomResponse response = CustomResponse.builder()
                .code(MessageCode.BAD_ARGUMENT.getCode())
                .customMessage(ve.getBody().getDetail())
                .path(request.getRequestURI())
                .details(errors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
