package com.yomou.controller;

import com.yomou.entity.Post;
import com.yomou.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostsController {

    private final PostsService postsService;

    @GetMapping()
    public List<Post> getRecentlyPosts(){
        return postsService.getRecentlyPosts();
    }

}
