package com.RealTime.Chatting.global.sms.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseNaverCloudSMS {
    String requestId;
    LocalDateTime requestTime;
    String statusCode;
    String statusName;

    public ResponseNaverCloudSMS() {
        super();
    }

    @Builder
    public ResponseNaverCloudSMS(String requestId, LocalDateTime requestTime, String statusCode, String statusName) {
        this.requestId = requestId;
        this.requestTime = requestTime;
        this.statusCode = statusCode;
        this.statusName = statusName;
    }
}
