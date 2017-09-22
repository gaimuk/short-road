package com.gaimuk.shortroad.controller;

import com.gaimuk.shortroad.common.exception.UrlNotFoundException;
import com.gaimuk.shortroad.controller.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Global ControllerAdvice to handle exception
 */
@ControllerAdvice
public class GlobalControllerAdvice {

    /**
     * Handle error when no matching URL pair can be found
     *
     * @return
     */
    @ExceptionHandler({UrlNotFoundException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleUrlNotFoundException() {
        return new ErrorResponse("URL is invalid");
    }

    /**
     * Handle validation error with HTTP 400 and the first field name
     *
     * @param e
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleValidationError(MethodArgumentNotValidException e) {
        final String firstErrorField = e.getBindingResult().getFieldErrors().stream().findFirst().get().getField();
        return new ErrorResponse("This parameter is invalid: " + firstErrorField);
    }

    /**
     * Handle no handler found error at dispatcher level with HTTP 404
     */
    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void handleNoHandlerError() {

    }

    /**
     * Handle all runtime exception as HTTP 500
     */
    @ExceptionHandler({Throwable.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleRuntimeException() {

    }

}
