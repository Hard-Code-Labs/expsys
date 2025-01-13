package ec.com.expensys.web.exception;

import ec.com.expensys.web.utils.MessageCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomResponse> handlerException(CustomException ce, HttpServletRequest request) {

        if (ce.isSendToLog()) {
            log.error("Error en la clase {} -> {}", ce.getClassName(), ce.getMessage());
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

        Map<String, String> errors = new HashMap<>();
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

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomResponse> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        CustomResponse response = CustomResponse.builder()
                .code(MessageCode.FORBIDDEN.getCode())
                .customMessage("Access denied: " + ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CustomResponse> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        CustomResponse response = CustomResponse.builder()
                .code(MessageCode.UNAUTHORIZED.getCode())
                .customMessage("Authentication failed: " + ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
        CustomResponse response = CustomResponse.builder()
                .code(MessageCode.PERSIST_ERROR.getCode())
                .customMessage("Database integrity constraint violated")
                .path(request.getRequestURI())
                .details(Map.of("error", ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage()))
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomResponse> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        Map<String, String> details = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage
                ));

        CustomResponse response = CustomResponse.builder()
                .code(MessageCode.PERSIST_ERROR.getCode())
                .customMessage("Validation failed")
                .path(request.getRequestURI())
                .details(details)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponse> handleGenericException(Exception ex, HttpServletRequest request) {

        CustomResponse response = CustomResponse.builder()
                .code(MessageCode.SERVER_ERROR.getCode())
                .customMessage("An unexpected server error occurred")
                .path(request.getRequestURI())
                .details(Map.of("error", ex.getMessage()))
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
