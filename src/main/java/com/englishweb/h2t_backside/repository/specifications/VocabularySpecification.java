package com.englishweb.h2t_backside.repository.specifications;

import com.englishweb.h2t_backside.model.enummodel.WordTypeEnum;
import com.englishweb.h2t_backside.model.lesson.Vocabulary;
import org.springframework.data.jpa.domain.Specification;

public class VocabularySpecification {
    public static Specification<Vocabulary> findByWord(String word) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("word"), "%" + word + "%");
    }

    public static Specification<Vocabulary> findByWordType(WordTypeEnum wordType) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("wordType"), wordType);
    }

    public static Specification<Vocabulary> findByTopicId(Long topicId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("topic").get("id"), topicId);
    }
}
