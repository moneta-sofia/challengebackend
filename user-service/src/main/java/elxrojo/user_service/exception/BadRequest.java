package elxrojo.user_service.exception;

public class BadRequest extends RuntimeException{
    public BadRequest(String message) {
        super(message);
    }
}
