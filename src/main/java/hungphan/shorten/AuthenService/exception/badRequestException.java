package hungphan.shorten.AuthenService.exception;

public class badRequestException extends RuntimeException{
    private String message;
    public badRequestException(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
