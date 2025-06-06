package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.SubmitToeicPart1;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SubmitToeicPart1Repository extends JpaRepository<SubmitToeicPart1, Long> {
    List<SubmitToeicPart1> findBySubmitToeicIdAndToeicPart1Id(Long submitToeicId, Long toeicPart1Id);
    List<SubmitToeicPart1> findBySubmitToeicIdAndToeicPart1IdIn(Long submitToeicId, List<Long> toeicPart1Ids);

    List<SubmitToeicPart1> findBySubmitToeicId(Long submitToeicId);
}