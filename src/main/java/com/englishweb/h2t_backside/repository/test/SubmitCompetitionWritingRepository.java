package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.SubmitCompetitionWriting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SubmitCompetitionWritingRepository extends JpaRepository<SubmitCompetitionWriting, Long> {
    List<SubmitCompetitionWriting> findBySubmitCompetitionIdAndTestWritingId(Long submitCompetitionId, Long testWritingId);
    List<SubmitCompetitionWriting> findBySubmitCompetitionIdAndTestWritingIdIn(Long submitCompetitionId, List<Long> testWritingIds);

    List<SubmitCompetitionWriting> findBySubmitCompetitionId(Long submitCompetitionId);
}