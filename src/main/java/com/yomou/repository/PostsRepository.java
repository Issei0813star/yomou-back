package com.yomou.repository;

import com.yomou.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Post, Long> {
    List<Post> findFirst10ByOrderByPostedAtDesc();
}
