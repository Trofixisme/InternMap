package com.group.InternMap.ResponseError;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;

//Targets only REST controllers
@RestControllerAdvice
public class RestErrorController {

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse processFailed(DataIntegrityViolationException e) {
        return ErrorResponse.create(e, HttpStatus.FORBIDDEN, e.getMessage());
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, String>> handleGeneralException(Exception e) {
//        Map<String, String> error = new HashMap<>();
//        error.put("error", "Internal Server Error");
//        error.put("message", e.getMessage());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//    }
}