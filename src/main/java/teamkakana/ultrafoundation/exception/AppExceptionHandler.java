package teamkakana.ultrafoundation.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import teamkakana.ultrafoundation.util.DateTimeUtil;

@ControllerAdvice
@RequiredArgsConstructor
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    private final DateTimeUtil dateTimeUtil;

    @ExceptionHandler(UserAlreadyExist.class)
    public ResponseEntity<Object> handlerUserAlreadyExist(UserAlreadyExist exception) {
        return new ResponseEntity<Object>(new ApiError(exception.getMessage(), HttpStatus.BAD_REQUEST, dateTimeUtil.currentDate()), HttpStatus.BAD_REQUEST);
    }
}