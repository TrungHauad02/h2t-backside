package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.ToeicPart1;
import com.englishweb.h2t_backside.model.test.ToeicPart3_4;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToeicPart3_4Repository extends JpaRepository<ToeicPart3_4, Long> {
    List<ToeicPart3_4> findByIdInAndStatus(List<Long> ids, Boolean status);
}
