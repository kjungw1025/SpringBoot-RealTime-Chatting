package com.RealTime.Chatting.user.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SMSAuth {
    private final String phone;
    private final String code;
}
