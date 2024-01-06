package com.RealTime.Chatting.user.service;

import com.RealTime.Chatting.user.exception.UserNotFoundException;
import com.RealTime.Chatting.user.model.UserStatus;
import com.RealTime.Chatting.user.model.entity.User;
import com.RealTime.Chatting.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserWithdrawService {
    private final UserRepository userRepository;

    @Transactional
    public void withdraw(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.changeStatus(UserStatus.INACTIVE);
    }
}
