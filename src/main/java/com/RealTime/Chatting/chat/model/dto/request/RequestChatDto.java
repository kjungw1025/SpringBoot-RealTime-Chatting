package com.RealTime.Chatting.chat.model.dto.request;

import com.RealTime.Chatting.chat.model.MessageType;
import lombok.*;

@Getter
@RequiredArgsConstructor
public class RequestChatDto {

    private final MessageType type;

    private final String roomId;

    private final String sender;

    private final String message;

//    private String time;
//
//    /* 파일 업로드 관련 변수 (일단 보류) */
//    private String s3DataUrl; // 파일 업로드 url
//    private String fileName; // 파일이름
//    private String fileDir; // s3 파일 경로
}
