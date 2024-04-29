package com.yomou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.yomou.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u WHERE u.email = :userId OR u.userName = :userId")
    UserEntity findUser(String userId);

    Boolean existsByEmail(String email);
}
