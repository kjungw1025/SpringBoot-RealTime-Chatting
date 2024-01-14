package com.RealTime.Chatting.chat.service;

import com.RealTime.Chatting.chat.model.dto.Message;
import com.RealTime.Chatting.chat.model.dto.response.ResponseChatDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageReceiver {
    private final SimpMessageSendingOperations template;

    @KafkaListener(topics = "chatting", containerFactory = "kafkaConsumerContainerFactory")
    public void receiveMessage(String stringChat) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        Message message = objectMapper.readValue(stringChat, Message.class);
        log.info("Consumed Message : " + stringChat);

        // 메시지 객체 내부의 채팅방 번호를 참조하여, 해당 채팅방 구독자에게 메시지를 발송
        template.convertAndSend("/sub/chatRoom/enter" + message.getRoomId(), message);
    }
}
