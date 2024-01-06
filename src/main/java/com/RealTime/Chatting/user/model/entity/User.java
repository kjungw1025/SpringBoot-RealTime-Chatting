package com.RealTime.Chatting.user.model.entity;

import com.RealTime.Chatting.global.base.BaseEntity;
import com.RealTime.Chatting.global.auth.role.UserRole;
import com.RealTime.Chatting.user.model.UserStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    public static String DELETED_USER = "탈퇴한 회원";

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @NotNull
    @Column(length = 20)
    private String name;

    @NotNull
    private String nickname;

    @NotNull
    private String password;

    @NotNull
    private String phone;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Builder
    private User(@NotNull String name,
                 @NotNull String nickname,
                 @NotNull String password,
                 @NotNull String phone,
                 UserRole userRole,
                 UserStatus status) {
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.phone = phone;
        this.userRole = userRole;
        this.status = status;
    }

    public String getName() {
        if (!getStatus().isActive()) {
            return DELETED_USER;
        }
        return this.name;
    }

    public String getNickName() {
        if (!getStatus().isActive()) {
            return DELETED_USER;
        }
        return this.nickname;
    }

    /**
     * Member 상태를 변경합니다.
     *
     * @param status 상태
     */
    public void changeStatus(UserStatus status) {
        this.status = status;
    }

    /**
     * 이름을 변경합니다.
     *
     * @param name 이름
     */
    public void changeName(String name) {
        this.name = name;
    }

    /**
     * 닉네임(아이디)를 변경합니다.
     *
     * @param nickname 닉네임(아이디)
     */
    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 탈퇴한 Member의 정보를 수정합니다.
     */
    public void emptyOutUserInfo() {
        this.name = "";
        this.phone = "";
        this.nickname = "";
        this.password = "";
    }
}
