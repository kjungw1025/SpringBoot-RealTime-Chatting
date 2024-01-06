package com.RealTime.Chatting.global.auth.jwt;

import com.RealTime.Chatting.global.auth.role.UserRole;
import org.springframework.security.core.Authentication;

public interface AppAuthentication extends Authentication {
    Long getUserId();

    UserRole getUserRole();

    boolean isAdmin();
}
