package com.RealTime.Chatting.user.repository;

import com.RealTime.Chatting.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<com.RealTime.Chatting.user.model.entity.User, Long> , JpaSpecificationExecutor<com.RealTime.Chatting.user.model.entity.User> {

    @Query("select u from User u where u.status = 'ACTIVE' and u.id = :id")
    Optional<com.RealTime.Chatting.user.model.entity.User> findById(@Param("id") Long id);

    @Query("select u from User u where u.status = 'ACTIVE' and u.phone = :phone")
    Optional<com.RealTime.Chatting.user.model.entity.User> findByPhone(@Param("phone") String phone);

    @Query("select u from User u where u.status = 'ACTIVE' and u.nickname = :nickname")
    Optional<com.RealTime.Chatting.user.model.entity.User> findByNickname(@Param("nickname") String nickname);

    @Query("select u from User u where u.status = 'ACTIVE' and u.name = :name")
    Optional<com.RealTime.Chatting.user.model.entity.User> findByName(String name);

    @Query("select u from User u where u.status = 'INACTIVE' and u.phone = :phone")
    Optional<com.RealTime.Chatting.user.model.entity.User> findByInactiveByPhone(String phone);
}
