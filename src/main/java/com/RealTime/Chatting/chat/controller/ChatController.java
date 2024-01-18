package com.RealTime.Chatting.chat.controller;

import com.RealTime.Chatting.chat.model.MessageType;
import com.RealTime.Chatting.chat.model.dto.request.RequestChatDto;
import com.RealTime.Chatting.chat.model.dto.response.ResponseChatDto;
import com.RealTime.Chatting.chat.service.ChatService;
import com.RealTime.Chatting.global.auth.jwt.AppAuthentication;
import com.RealTime.Chatting.global.auth.role.UserAuth;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "채팅", description = "채팅 송/수신 관련 api")
@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    // 아래에서 사용되는 convertAndSend 를 사용하기 위한 선언
    // convertAndSend 는 객체를 인자로 넘겨주면 자동으로 Message 객체로 변환 후 도착지로 전송한다.
    private final SimpMessageSendingOperations template;

    private final ChatService chatService;

    // MessageMapping 을 통해 webSocket 로 들어오는 메시지를 발신 처리한다.
    // 이때 클라이언트에서는 /pub/chat/sendMessage 로 요청하게 되고 이것을 controller 가 받아서 처리한다.
    // 처리가 완료되면 /sub/chatRoom/enter/roomId 로 메시지가 전송된다.
    @MessageMapping("/chat/enterUser")
    public void enterUser(@Payload RequestChatDto chat,
                          SimpMessageHeaderAccessor headerAccessor) {
        // 채팅방 유저+1
        chatService.plusUserCnt(chat.getRoomId());
        System.out.println("enterUser - Session ID: " + headerAccessor.getSessionId());

        // 채팅방에 유저 추가 및 UserUUID 반환
        String username = chatService.addUser(chat.getRoomId(), chat.getSender());
        log.info("enterUser에서 uuid " + username);
        log.info("enterUser에서 roomId " + chat.getRoomId());

        // 반환 결과를 socket session 에 userUUID 로 저장
        headerAccessor.getSessionAttributes().put("username", username);
        headerAccessor.getSessionAttributes().put("roomId", chat.getRoomId());

        ResponseChatDto responseChatDto = ResponseChatDto.builder()
                .type(chat.getType())
                .roomId(chat.getRoomId())
                .sender(chat.getSender())
                .message(chat.getMessage())
                .build();
        responseChatDto.changeMessage(responseChatDto.getSender() + " 님 입장!!");

        template.convertAndSend("/sub/chatRoom/enter" + responseChatDto.getRoomId(), responseChatDto);
    }

    // 해당 유저
    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload RequestChatDto chat) {
        log.info("CHAT {}", chat);

        ResponseChatDto responseChatDto = ResponseChatDto.builder()
                .type(chat.getType())
                .roomId(chat.getRoomId())
                .sender(chat.getSender())
                .message(chat.getMessage())
                .build();
        responseChatDto.changeMessage(responseChatDto.getMessage());

        template.convertAndSend("/sub/chatRoom/enter" + responseChatDto.getRoomId(), responseChatDto);
    }

    // 유저 퇴장 시에는 EventListener 을 통해서 유저 퇴장을 확인
    @EventListener
    public void webSocketDisconnectListener(SessionDisconnectEvent event) {
        log.info("DisConnEvent {}", event);

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // stomp 세션에 있던 username과 roomId 를 확인해서 채팅방 유저 리스트와 room 에서 해당 유저를 삭제
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");
        log.info("퇴장 controller에서 uuid " + username);
        log.info("퇴장 controller에서 roomId " + roomId);

        log.info("headAccessor {}", headerAccessor);

        // 채팅방 유저 -1
        chatService.minusUserCnt(roomId);

        // 채팅방 유저 리스트에서 유저 삭제
        chatService.delUser(roomId, username);

        if (username != null) {
            log.info("User Disconnected : ", username);

            // builder 어노테이션 활용
            ResponseChatDto chat = ResponseChatDto.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .roomId(roomId)
                    .message(username + " 님 퇴장!!")
                    .build();

            template.convertAndSend("/sub/chatRoom/enter" + roomId, chat);
        }
    }

    /**
     * 채팅에 참여한 유저 리스트 반환
     *
     * @param roomId    채팅방 id
     */
    @GetMapping("/chat/userlist")
    @UserAuth
    @ResponseBody
    public List<String> userList(String roomId) {
        return chatService.getUserList(roomId);
    }
}
