package com.englishweb.h2t_backside.repository;

import com.englishweb.h2t_backside.model.features.AIResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AIResponseRepository extends JpaRepository<AIResponse, Long> , JpaSpecificationExecutor<AIResponse> {
}
