package com.RealTime.Chatting.user.exception;

import com.RealTime.Chatting.global.error.exception.LocalizedMessageException;
import org.springframework.http.HttpStatus;

public class WrongSMSCodeException extends LocalizedMessageException {

    public WrongSMSCodeException(){
        super(HttpStatus.FORBIDDEN, "required.sms-authorization");
    }
}
