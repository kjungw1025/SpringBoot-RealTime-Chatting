package com.RealTime.Chatting.user.model.dto.response;

import lombok.Getter;

@Getter
public class ResponseSignupTokenDto {

    private final String signupToken;

    public ResponseSignupTokenDto(String signupToken) {
        this.signupToken = signupToken;
    }
}
