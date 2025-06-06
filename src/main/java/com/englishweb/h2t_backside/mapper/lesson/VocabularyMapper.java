package com.englishweb.h2t_backside.mapper.lesson;

import com.englishweb.h2t_backside.dto.lesson.VocabularyDTO;
import com.englishweb.h2t_backside.model.lesson.Topic;
import com.englishweb.h2t_backside.model.lesson.Vocabulary;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface VocabularyMapper {

    // Convert DTO -> Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    @Mapping(target = "example", source = "dto.example")
    @Mapping(target = "image", source = "dto.image")
    @Mapping(target = "word", source = "dto.word")
    @Mapping(target = "meaning", source = "dto.meaning")
    @Mapping(target = "phonetic", source = "dto.phonetic")
    @Mapping(target = "wordType", source = "dto.wordType", defaultValue = "NOUN")
    @Mapping(target = "topic", source = "dto.topicId", qualifiedByName = "mapTopicIdToTopic")
    Vocabulary convertToEntity(VocabularyDTO dto);

    // Convert Entity -> DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "example", source = "entity.example")
    @Mapping(target = "image", source = "entity.image")
    @Mapping(target = "word", source = "entity.word")
    @Mapping(target = "meaning", source = "entity.meaning")
    @Mapping(target = "phonetic", source = "entity.phonetic")
    @Mapping(target = "wordType", source = "entity.wordType")
    @Mapping(target = "topicId", source = "entity.topic.id")
    VocabularyDTO convertToDTO(Vocabulary entity);

    // Cập nhật Entity từ DTO
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "example", source = "dto.example")
    @Mapping(target = "image", source = "dto.image")
    @Mapping(target = "word", source = "dto.word")
    @Mapping(target = "meaning", source = "dto.meaning")
    @Mapping(target = "phonetic", source = "dto.phonetic")
    @Mapping(target = "wordType", source = "dto.wordType")
    @Mapping(target = "topic", source = "dto.topicId", qualifiedByName = "mapTopicIdToTopic")
    void patchEntityFromDTO(VocabularyDTO dto, @MappingTarget Vocabulary entity);

    // Custom mapping methods
    @Named("mapTopicIdToTopic")
    default Topic mapTopicIdToTopic(Long topicId) {
        if (topicId == null) return null;
        Topic topic = new Topic();
        topic.setId(topicId);
        return topic;
    }
}
