package hungphan.shorten.AuthenService.exception;

import hungphan.shorten.AuthenService.payload.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class globalCustomException {
    @ExceptionHandler(userAlreadyExistException.class)
    public ResponseEntity<?> handleUserAlreadyExistException(Exception e) {
        BaseResponse response = new BaseResponse();
        response.setStatusCode(409);
        response.setData(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(badRequestException.class)
    public ResponseEntity<?> handleCustomException(Exception e) {
        BaseResponse response = new BaseResponse();
        response.setStatusCode(400);
        response.setData(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
