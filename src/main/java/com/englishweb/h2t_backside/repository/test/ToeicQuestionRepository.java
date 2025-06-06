package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.ToeicPart1;
import com.englishweb.h2t_backside.model.test.ToeicQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToeicQuestionRepository extends JpaRepository<ToeicQuestion, Long> {
    List<ToeicQuestion> findByIdInAndStatus(List<Long> ids, Boolean status);
}
