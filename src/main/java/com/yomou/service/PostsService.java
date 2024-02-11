package com.yomou.service;

import com.yomou.entity.Post;
import com.yomou.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;

    public List<Post> getRecentlyPosts(){
        return postsRepository.findFirst10ByOrderByPostedAtDesc();
    }
}
