package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.SubmitTestWriting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SubmitTestWritingRepository extends JpaRepository<SubmitTestWriting, Long> {
    List<SubmitTestWriting> findBySubmitTestIdAndTestWritingId(Long submitTestId, Long testWritingId);

    List<SubmitTestWriting> findBySubmitTestIdAndTestWriting_IdIn(Long submitTestId, List<Long> testWritingIds);

    List<SubmitTestWriting> findBySubmitTestId(Long submitTestId);
}

