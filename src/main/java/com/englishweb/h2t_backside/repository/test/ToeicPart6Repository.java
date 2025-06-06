package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.ToeicPart1;
import com.englishweb.h2t_backside.model.test.ToeicPart6;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToeicPart6Repository extends JpaRepository<ToeicPart6, Long> {
    List<ToeicPart6> findByIdInAndStatus(List<Long> ids, Boolean status);
}
