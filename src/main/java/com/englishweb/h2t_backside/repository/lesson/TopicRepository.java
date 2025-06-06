package com.englishweb.h2t_backside.repository.lesson;

import com.englishweb.h2t_backside.model.lesson.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TopicRepository extends JpaRepository<Topic, Long> , JpaSpecificationExecutor<Topic> {
}
