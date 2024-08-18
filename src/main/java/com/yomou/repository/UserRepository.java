package com.yomou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.yomou.entity.User;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = :userId OR u.userName = :userId")
    User findUser(String userId);

    Boolean existsByEmail(String email);

    Boolean existsByUserName(String userName);
}
