package com.RealTime.Chatting.chat.model.entity;

import com.RealTime.Chatting.chat.model.ChatRoomStatus;
import com.RealTime.Chatting.global.base.BaseEntity;
import com.RealTime.Chatting.user.model.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "chat_room")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "chat_room_id")
    private Long id;

    @NotNull
    private String roomId;

    @NotNull
    private String roomName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "room_manager_id")
    private User roomManager;

    @NotNull
    private int userCount;

    @NotNull
    private int maxUserCount;

    @NotNull
    private String roomPwd;

    private boolean secretCheck;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatRoomUser> users = new ArrayList<>();

    @Enumerated(STRING)
    private ChatRoomStatus chatRoomStatus;

    @Builder
    private ChatRoom(@NotNull String roomId,
                     @NotNull String roomName,
                     @NotNull int userCount,
                     @NotNull int maxUserCount,
                     @NotNull String roomPwd,
                     boolean secretCheck,
                     User roomManager) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.userCount = userCount;
        this.maxUserCount = maxUserCount;
        this.roomPwd = roomPwd;
        this.secretCheck = secretCheck;
        this.roomManager = roomManager;
        this.chatRoomStatus = ChatRoomStatus.ACTIVE;
    }

    public void changeRoomName(String newRoomName) {
        this.roomName = newRoomName;
    }

    public void addUserCount() {
        this.userCount++;
    }

    public void subUserCount() {
        this.userCount--;
    }

    public void markAsDeleted(boolean byAdmin) {
        this.chatRoomStatus = byAdmin ? ChatRoomStatus.DELETED_BY_ADMIN : ChatRoomStatus.DELETED;
    }

    public void markAsClosed() {
        this.chatRoomStatus = ChatRoomStatus.CLOSED;
    }
}
