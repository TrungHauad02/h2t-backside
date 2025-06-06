package com.englishweb.h2t_backside.mapper.lesson;

import com.englishweb.h2t_backside.dto.lesson.LessonAnswerDTO;
import com.englishweb.h2t_backside.model.lesson.LessonAnswer;
import com.englishweb.h2t_backside.model.lesson.LessonQuestion;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface LessonAnswerMapper {

    // Convert DTO -> Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "correct", source = "dto.correct")
    @Mapping(target = "question", source = "dto.questionId", qualifiedByName = "mapQuestionIdToQuestion")
    LessonAnswer convertToEntity(LessonAnswerDTO dto);

    // Convert Entity -> DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "content", source = "entity.content")
    @Mapping(target = "correct", source = "entity.correct")
    @Mapping(target = "questionId", source = "entity.question.id")
    LessonAnswerDTO convertToDTO(LessonAnswer entity);

    // Cập nhật Entity từ DTO
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "correct", source = "dto.correct")
    @Mapping(target = "question", source = "dto.questionId", qualifiedByName = "mapQuestionIdToQuestion")
    void patchEntityFromDTO(LessonAnswerDTO dto, @MappingTarget LessonAnswer entity);

    // Custom mapping methods
    @Named("mapQuestionIdToQuestion")
    default LessonQuestion mapQuestionIdToQuestion(Long questionId) {
        if (questionId == null ) return null;
        LessonQuestion question = new LessonQuestion();
        question.setId(questionId);
        return question;
    }
}