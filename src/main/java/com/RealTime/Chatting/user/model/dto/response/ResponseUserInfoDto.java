package com.RealTime.Chatting.user.model.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseUserInfoDto {
    private final String name;
    private final String nickname;
    private final String phone;
    private final boolean isAdmin;
}
