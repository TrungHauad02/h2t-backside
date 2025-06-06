package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.ToeicPart1;
import com.englishweb.h2t_backside.model.test.ToeicPart7;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToeicPart7Repository extends JpaRepository<ToeicPart7, Long> {
    List<ToeicPart7> findByIdInAndStatus(List<Long> ids, Boolean status);
}
