package com.RealTime.Chatting.user.exception;

import com.RealTime.Chatting.global.error.exception.LocalizedMessageException;
import org.springframework.http.HttpStatus;

public class NotSMSAuthorizedException extends LocalizedMessageException {

    public NotSMSAuthorizedException() {
        super(HttpStatus.FORBIDDEN, "required.sms-authorization");
    }
}
