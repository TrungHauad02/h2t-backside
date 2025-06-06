package com.englishweb.h2t_backside.mapper.lesson;

import com.englishweb.h2t_backside.dto.lesson.LessonQuestionDTO;
import com.englishweb.h2t_backside.model.lesson.LessonQuestion;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = LessonAnswerMapper.class,
        builder = @Builder(disableBuilder = true)
)
public interface LessonQuestionMapper {

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "explanation", source = "dto.explanation")
    @Mapping(target = "answers", source = "dto.answers")
    LessonQuestion convertToEntity(LessonQuestionDTO dto);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "content", source = "entity.content")
    @Mapping(target = "explanation", source = "entity.explanation")
    @Mapping(target = "answers", source = "entity.answers")
    LessonQuestionDTO convertToDTO(LessonQuestion entity);

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "explanation", source = "dto.explanation")
    @Mapping(target = "answers", source = "dto.answers")
    void patchEntityFromDTO(LessonQuestionDTO dto, @MappingTarget LessonQuestion entity);

    @AfterMapping
    default void afterConvertToEntity(LessonQuestionDTO dto, @MappingTarget LessonQuestion entity) {
        if (entity.getAnswers() != null) {
            entity.getAnswers().forEach(answer -> answer.setQuestion(entity));
        }
    }
}
