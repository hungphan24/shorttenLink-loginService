package hungphan.shorten.AuthenService.exception;

public class userAlreadyExistException extends RuntimeException{
    private String message;
    public userAlreadyExistException(String message) {
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
