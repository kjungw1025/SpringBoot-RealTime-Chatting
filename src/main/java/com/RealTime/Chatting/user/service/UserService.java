package com.RealTime.Chatting.user.service;

import com.RealTime.Chatting.global.auth.jwt.AuthenticationToken;
import com.RealTime.Chatting.global.auth.jwt.JwtAuthentication;
import com.RealTime.Chatting.global.auth.jwt.JwtProvider;
import com.RealTime.Chatting.user.exception.AutoLoginUserNotFoundException;
import com.RealTime.Chatting.user.exception.UserNotFoundException;
import com.RealTime.Chatting.user.exception.WrongPasswordException;
import com.RealTime.Chatting.user.model.dto.AutoLoginDto;
import com.RealTime.Chatting.user.model.dto.request.RequestLoginDto;
import com.RealTime.Chatting.user.model.dto.request.RequestReissueDto;
import com.RealTime.Chatting.user.model.dto.response.ResponseLoginDto;
import com.RealTime.Chatting.user.model.dto.response.ResponseRefreshTokenDto;
import com.RealTime.Chatting.user.model.dto.response.ResponseReissueDto;
import com.RealTime.Chatting.user.model.dto.response.ResponseUserInfoDto;
import com.RealTime.Chatting.user.model.entity.User;
import com.RealTime.Chatting.user.repository.AutoLoginRepository;
import com.RealTime.Chatting.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {

    public static final String AUTO_LOGIN_NAME = "auto-login";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AutoLoginRepository autoLoginRepository;

    public ResponseLoginDto login(RequestLoginDto dto) {
        Instant now = Instant.now();
        User user = userRepository.findByNickname(dto.getNickname())
                .orElseThrow(UserNotFoundException::new);

        if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            AuthenticationToken token = jwtProvider.issue(user);
            autoLoginRepository.setAutoLoginPayload(token.getRefreshToken(), AUTO_LOGIN_NAME,
                    new AutoLoginDto(user.getId().toString(), user.getUserRole()), now);
            return new ResponseLoginDto(token, user);
        } else {
            throw new WrongPasswordException();
        }
    }

    public ResponseRefreshTokenDto refreshToken(HttpServletRequest request, String refreshToken) {
        String accessToken = jwtProvider.getAccessTokenFromHeader(request);
        AuthenticationToken token = jwtProvider.reissue(accessToken, refreshToken);
        return new ResponseRefreshTokenDto(token);
    }

    public ResponseUserInfoDto getUserInfo(Long memberId) {
        User user = userRepository.findById(memberId).orElseThrow(UserNotFoundException::new);
        return new ResponseUserInfoDto(user.getName(), user.getNickName(), user.getPhone(), user.getUserRole().isAdmin());
    }
}
