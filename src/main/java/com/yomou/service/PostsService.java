package com.yomou.service;

import com.yomou.entity.RecommendTarget;
import com.yomou.entity.Post;
import com.yomou.repository.MstRecommendTargetRepository;
import com.yomou.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;
    private final MstRecommendTargetRepository mstRecommendTargetRepository;

    public List<Post> getRecentlyPosts(){
        return postsRepository.findFirst10ByOrderByPostedAtDesc();
    }

    public RecommendTarget saveMstRecommendTarget(String name) {
        RecommendTarget recommendTarget = new RecommendTarget();
        recommendTarget.setName(name);
        return mstRecommendTargetRepository.save(recommendTarget);
    }
}
