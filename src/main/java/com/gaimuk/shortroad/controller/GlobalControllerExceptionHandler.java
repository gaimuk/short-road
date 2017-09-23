package com.gaimuk.shortroad.controller;

import com.gaimuk.shortroad.common.exception.UrlNotFoundException;
import com.gaimuk.shortroad.controller.response.ErrorResponse;
import com.gaimuk.shortroad.controller.response.UrlNotFoundErrorResponse;
import com.gaimuk.shortroad.controller.response.ValidationErrorResponse;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;

/**
 * Global handler to handle controller-level exception
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    /**
     * Handle error when no matching URL can be found
     *
     * @return
     */
    @ExceptionHandler({UrlNotFoundException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleUrlNotFoundException() {
        return new UrlNotFoundErrorResponse();
    }

    /**
     * Handle validation error with HTTP 400
     *
     * @param e
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleValidationError(MethodArgumentNotValidException e) {
        // Build detail map
        ImmutableMap.Builder<String, String> detailBuilder = ImmutableMap.builder();
        e.getBindingResult().getFieldErrors().forEach(fieldError ->
                detailBuilder.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return new ValidationErrorResponse(detailBuilder.build());
    }

    /**
     * Handle custom validation error with HTTP 400
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleConstraintViolation(ConstraintViolationException e) {
        // Build detail map
        ImmutableMap.Builder<String, String> detailBuilder = ImmutableMap.builder();
        e.getConstraintViolations().forEach(violation ->
                detailBuilder.put(violation.getInvalidValue().toString(), violation.getMessage()));

        return new ValidationErrorResponse(detailBuilder.build());
    }

    /**
     * Handle invalid path error at dispatcher level with HTTP 404
     * Caught by invalid URL endpoint
     */
    @ExceptionHandler({NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void handleInvalidPathError() {

    }

    /**
     * Handle all runtime exception as HTTP 500
     */
    @ExceptionHandler({Throwable.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleRuntimeException(Throwable e) {
        log.error("RuntimeException occurred, safe error response return to caller", e);
    }

}
