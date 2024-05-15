package com.claims.claimsrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class CreatedAndUpdatedDateTimeException extends RuntimeException {

    public CreatedAndUpdatedDateTimeException(String message){
        super(message);
    }
}
