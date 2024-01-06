package com.RealTime.Chatting.user.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
public class RequestVerifySMSCodeDto {

    @NotBlank
    @Schema(description = "인증 코드", example = "123456")
    private String code;

    public RequestVerifySMSCodeDto() {
    }

    public RequestVerifySMSCodeDto(String code) {
        this.code = code;
    }
}
