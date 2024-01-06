package com.RealTime.Chatting.user.exception;

import com.RealTime.Chatting.global.error.exception.LocalizedMessageException;
import org.springframework.http.HttpStatus;

public class NotSupportedMethodException extends LocalizedMessageException {

    public NotSupportedMethodException(Throwable e){
        super(e, HttpStatus.BAD_REQUEST, "notsupport.http-method");
    }
}
