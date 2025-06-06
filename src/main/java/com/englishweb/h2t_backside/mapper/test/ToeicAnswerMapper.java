package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.ToeicAnswerDTO;
import com.englishweb.h2t_backside.model.test.ToeicAnswer;
import com.englishweb.h2t_backside.model.test.ToeicQuestion;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface ToeicAnswerMapper {

    // Convert DTO → Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "correct", source = "dto.correct")
    @Mapping(target = "question", source = "dto.questionId", qualifiedByName = "mapQuestionIdToQuestion")
    ToeicAnswer convertToEntity(ToeicAnswerDTO dto);

    // Convert Entity → DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "content", source = "entity.content")
    @Mapping(target = "correct", source = "entity.correct")
    @Mapping(target = "questionId", source = "entity.question.id")
    ToeicAnswerDTO convertToDTO(ToeicAnswer entity);

    // Patch DTO → Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "correct", source = "dto.correct")
    @Mapping(target = "question", source = "dto.questionId", qualifiedByName = "mapQuestionIdToQuestion")
    void patchEntityFromDTO(ToeicAnswerDTO dto, @MappingTarget ToeicAnswer entity);

    // Custom mapping methods
    @Named("mapQuestionIdToQuestion")
    default ToeicQuestion mapQuestionIdToQuestion(Long id) {
        if (id == null) return null;
        ToeicQuestion question = new ToeicQuestion();
        question.setId(id);
        return question;
    }
}
