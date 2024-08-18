package com.yomou.controller;

import com.yomou.entity.Post;
import com.yomou.entity.RecommendTarget;
import com.yomou.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostsController {

    private final PostsService postsService;

    @GetMapping()
    public List<Post> getRecentlyPosts(){
        return postsService.getRecentlyPosts();
    }

    @PostMapping("/mst/recommendTarget")
    public RecommendTarget saveMstRecommendTarget(@RequestBody Map<String, String> req) {
        return postsService.saveMstRecommendTarget(req.get("name"));
    }

}
