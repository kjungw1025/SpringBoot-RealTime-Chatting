package com.RealTime.Chatting.user.controller;

import com.RealTime.Chatting.global.auth.jwt.AppAuthentication;
import com.RealTime.Chatting.global.auth.role.UserAuth;
import com.RealTime.Chatting.user.model.dto.request.RequestLoginDto;
import com.RealTime.Chatting.user.model.dto.request.RequestReissueDto;
import com.RealTime.Chatting.user.model.dto.request.RequestSignupDto;
import com.RealTime.Chatting.user.model.dto.response.*;
import com.RealTime.Chatting.user.service.SignupService;
import com.RealTime.Chatting.user.service.UserService;
import com.RealTime.Chatting.user.service.UserWithdrawService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Tag(name = "사용자", description = "사용자 관련 api")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;
    private final UserWithdrawService userWithdrawService;
    private final SignupService signupService;

    /**
     * 회원가입 토큰 생성
     *
     * @return 회원가입 토큰
     */
    @GetMapping("/signup-token")
    public ResponseSignupTokenDto generateSignupToken() {
        return signupService.generateSignupToken();
    }

    /**
     * 회원가입
     *
     * @param dto           요청 body
     * @param signupToken   회원가입 토큰
     */
    @PostMapping("/{signup-token}")
    public void signup(@Valid @RequestBody RequestSignupDto dto,
                       @PathVariable("signup-token") String signupToken) {
        signupService.signup(dto, signupToken);
    }

    /**
     * 로그인
     *
     * @param dto           요청 body
     * @return              로그인 인증 정보
     */
    @PostMapping("/login")
    public ResponseLoginDto login(@Valid @RequestBody RequestLoginDto dto) {
        return userService.login(dto);
    }

    /**
     * 토큰 재발급
     *
     * @param refreshToken  멤버의 refreshToken
     * @return 재발급된 로그인 인증 정보
     */
    @PostMapping("/reissue")
    @UserAuth
    public ResponseRefreshTokenDto refreshToken(HttpServletRequest request,
                                                @Valid @RequestParam String refreshToken) {
        return userService.refreshToken(request, refreshToken);
    }

    /**
     * 닉네임 중복 확인
     * @param nickname      닉네임
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "중복되는 닉네임이 없습니다."),
            @ApiResponse(responseCode = "500", description = "already.nickname")
    })
    @PostMapping("/signup/verify/{nickname}")
    public void verifyNickname(@PathVariable("nickname") String nickname) {
        signupService.checkAlreadyNickname(nickname);
    }

    /**
     * 내 정보 조회
     *
     *
     */
    @GetMapping
    @UserAuth
    public ResponseUserInfoDto getMemberInfo(AppAuthentication auth) {
        return userService.getUserInfo(auth.getUserId());
    }

    /**
     * 회원탈퇴
     * <p>회원은 바로 삭제되지 않고(비활성화로 전환), 일정 기간 뒤에 삭제됩니다.</p>
     */
    @DeleteMapping
    @UserAuth
    public void withdraw(AppAuthentication auth) {
        userWithdrawService.withdraw(auth.getUserId());
    }
}
