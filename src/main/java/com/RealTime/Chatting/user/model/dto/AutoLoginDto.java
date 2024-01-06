package com.RealTime.Chatting.user.model.dto;

import com.RealTime.Chatting.global.auth.role.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AutoLoginDto {

    private final String userId;
    private final UserRole userRole;
}
