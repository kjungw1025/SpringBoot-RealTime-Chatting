package com.RealTime.Chatting.user.exception;

import com.RealTime.Chatting.global.error.exception.LocalizedMessageException;
import org.springframework.http.HttpStatus;

public class AlreadyPhoneException extends LocalizedMessageException {

    public AlreadyPhoneException() {
        super(HttpStatus.BAD_REQUEST, "already.phone");
    }
}
