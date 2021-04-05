package net.thumbtack.school.springrest.errorhandler;

import net.thumbtack.school.springrest.dto.ResponseError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseError handleErrorValidation(MethodArgumentNotValidException exc) {
        final ResponseError error = new ResponseError();
        exc.getBindingResult().getFieldErrors().forEach(fieldError-> {
            error.getAllErrors().add(String.format("%s:%s", fieldError.getField(), fieldError.getDefaultMessage()));
        });
        exc.getBindingResult().getGlobalErrors().forEach(err-> {
            error.getAllErrors().add(String.format("global:%s", err.getDefaultMessage()));
        });
        return error;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    @ResponseBody
    public ResponseError handleErrorIllegalState(IllegalStateException exc) {
        final ResponseError error = new ResponseError();
        error.getAllErrors().add(exc.getMessage());
        return error;
    }

}
