package com.RealTime.Chatting.global.auth.role;

import com.RealTime.Chatting.global.auth.jwt.JwtProvider;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.annotation.Secured;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SecurityRequirement(name = JwtProvider.AUTHORIZATION)
@Secured(UserAuthNames.ROLE_USER)
public @interface UserAuth {
}
