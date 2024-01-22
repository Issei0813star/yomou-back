package com.yomou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.yomou.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
