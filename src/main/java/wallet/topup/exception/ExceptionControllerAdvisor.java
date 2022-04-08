package wallet.topup.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wallet.topup.dto.ErrorInfoDTO;
import wallet.topup.enums.Error;

import javax.validation.ValidationException;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvisor {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorInfoDTO> handleValidationException(ValidationException exception) {
        log.error("Validation exception occurred with errors: {}", exception.getMessage());
        return new ResponseEntity<>(new ErrorInfoDTO(Error.INVALID_REQUEST.getCode(), exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TopUpException.class)
    public ResponseEntity<ErrorInfoDTO> handleTopUpException(TopUpException exception) {
        log.error("TopUp exception occurred with errors: {}", exception.getMsg());
        return new ResponseEntity<>(new ErrorInfoDTO(exception.getCode(), exception.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorInfoDTO> handleNotFoundException(NotFoundException exception) {
        log.error("NotFound exception occurred with errors: {}", exception.getMsg());
        return new ResponseEntity<>(new ErrorInfoDTO(exception.getCode(), exception.getMsg()), HttpStatus.NOT_FOUND);
    }
}
