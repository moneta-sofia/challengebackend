package elxrojo.account_service.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int statusCode;
    private String message;
    private String details;

    public ErrorResponse(LocalDateTime timestamp, int statusCode, String message, String details) {
        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.message = message;
        this.details = details;
    }
}



