package com.RealTime.Chatting.user.exception;

import com.RealTime.Chatting.global.error.exception.LocalizedMessageException;
import org.springframework.http.HttpStatus;

public class BadRequestException extends LocalizedMessageException {

    public BadRequestException(Throwable t) {
        super(t, HttpStatus.BAD_REQUEST, "invalid.request");
    }
}
