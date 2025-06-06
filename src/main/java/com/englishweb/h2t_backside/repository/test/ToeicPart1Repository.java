package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.ToeicPart1;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ToeicPart1Repository extends JpaRepository<ToeicPart1, Long> {
    List<ToeicPart1> findByIdInAndStatus(List<Long> ids, Boolean status);
}
