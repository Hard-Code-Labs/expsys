package ec.com.expensys.web.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int code;
    private LocalDateTime timestamp;
    private String message;
    private String path;
    private Map<String, String> details;
}
