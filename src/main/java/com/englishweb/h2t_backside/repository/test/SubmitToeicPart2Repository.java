package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.SubmitToeicPart2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SubmitToeicPart2Repository extends JpaRepository<SubmitToeicPart2, Long> {
    List<SubmitToeicPart2> findBySubmitToeicIdAndToeicPart2Id(Long submitToeicId, Long toeicPart2Id);
    List<SubmitToeicPart2> findBySubmitToeicIdAndToeicPart2IdIn(Long submitToeicId, List<Long> toeicPart2Ids);

    List<SubmitToeicPart2> findBySubmitToeicId(Long submitToeicId);
}