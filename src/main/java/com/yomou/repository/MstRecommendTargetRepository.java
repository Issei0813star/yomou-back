package com.yomou.repository;

import com.yomou.entity.RecommendTarget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MstRecommendTargetRepository extends JpaRepository<RecommendTarget, Long> {

}