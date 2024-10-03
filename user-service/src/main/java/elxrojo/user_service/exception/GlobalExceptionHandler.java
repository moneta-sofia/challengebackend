package elxrojo.user_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse(
                    LocalDateTime.now(),
                    "An unexpected error occurred :(",
                    ex.getMessage()
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        @ExceptionHandler(BadRequest.class)
        public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequest ex) {
            ErrorResponse errorResponse = new ErrorResponse(
                    LocalDateTime.now(),
                    "Bad Request",
                    ex.getMessage()
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
}
