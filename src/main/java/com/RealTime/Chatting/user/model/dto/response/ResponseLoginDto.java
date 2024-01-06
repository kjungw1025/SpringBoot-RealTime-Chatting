package com.RealTime.Chatting.user.model.dto.response;

import com.RealTime.Chatting.global.auth.jwt.AuthenticationToken;
import com.RealTime.Chatting.user.model.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class ResponseLoginDto {
    private final String accessToken;
    private final String refreshToken;
    private final String name;
    private final String phone;
    private final boolean isAdmin;

    public ResponseLoginDto(AuthenticationToken token, User user) {
        this.accessToken = token.getAccessToken();
        this.refreshToken = token.getRefreshToken();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.isAdmin = user.getUserRole().isAdmin();
    }
}
