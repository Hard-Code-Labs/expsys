package ec.com.expensys.web.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final LocalDateTime timestamp = LocalDateTime.now();

    private int code;
    private String customMessage;
    private String path;
    private Map<String, String> details;
}
