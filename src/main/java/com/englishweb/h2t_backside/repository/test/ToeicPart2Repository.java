package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.Toeic;
import com.englishweb.h2t_backside.model.test.ToeicPart1;
import com.englishweb.h2t_backside.model.test.ToeicPart2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToeicPart2Repository extends JpaRepository<ToeicPart2, Long> {
    List<ToeicPart2> findByIdInAndStatus(List<Long> ids, Boolean status);
}
