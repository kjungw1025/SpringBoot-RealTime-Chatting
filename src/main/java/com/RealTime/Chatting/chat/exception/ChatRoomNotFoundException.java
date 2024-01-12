package com.RealTime.Chatting.chat.exception;

import com.RealTime.Chatting.global.error.exception.LocalizedMessageException;
import org.springframework.http.HttpStatus;

public class ChatRoomNotFoundException extends LocalizedMessageException {
    public ChatRoomNotFoundException() { super(HttpStatus.NOT_FOUND, "notfound.chat-room"); }
}
