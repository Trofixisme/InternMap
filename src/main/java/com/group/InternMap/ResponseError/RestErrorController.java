package com.group.InternMap.ResponseError;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.client.HttpClientErrorException;
import java.util.MissingResourceException;

//Targets only REST controllers
@RestControllerAdvice
public class RestErrorController {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorResponse processFailed(DataIntegrityViolationException e) {
        return ErrorResponse.create(e, HttpStatus.BAD_GATEWAY, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse processFailed(Exception e) {
        return ErrorResponse.create(e, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(MissingResourceException.class)
    public ErrorResponse resourceNotFound(MissingResourceException e) {
        return ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ErrorResponse unauthorisedAccess(HttpClientErrorException e) {
        return ErrorResponse.create(e, HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse generalException(Exception e) {
        return ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
    }
}