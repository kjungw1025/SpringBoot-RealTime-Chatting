package com.RealTime.Chatting.global.error.exception;

import org.springframework.http.HttpStatus;

public class ExpiredTokenException extends LocalizedMessageException {

    public ExpiredTokenException() {
        super(HttpStatus.NOT_ACCEPTABLE, "invalid.expired-token");
    }
}
