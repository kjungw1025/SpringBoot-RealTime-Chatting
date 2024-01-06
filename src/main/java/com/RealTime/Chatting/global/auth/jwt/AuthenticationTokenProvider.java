package com.RealTime.Chatting.global.auth.jwt;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationTokenProvider {
    String getAccessTokenFromHeader(HttpServletRequest request);

    Authentication getAuthentication(String accessToken);
}
