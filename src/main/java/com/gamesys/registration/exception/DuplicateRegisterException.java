package com.gamesys.registration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateRegisterException extends RuntimeException {

    public DuplicateRegisterException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
