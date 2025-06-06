package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.QuestionDTO;
import com.englishweb.h2t_backside.dto.test.ToeicQuestionDTO;
import com.englishweb.h2t_backside.model.test.Question;
import com.englishweb.h2t_backside.model.test.ToeicQuestion;
import com.englishweb.h2t_backside.model.test.ToeicAnswer;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = ToeicAnswerMapper.class,
        builder = @Builder(disableBuilder = true)
)
public interface ToeicQuestionMapper {

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "explanation", source = "dto.explanation")
    @Mapping(target = "answers", source = "dto.answers")
    ToeicQuestion convertToEntity(ToeicQuestionDTO dto);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "content", source = "entity.content")
    @Mapping(target = "explanation", source = "entity.explanation")
    @Mapping(target = "answers", source = "entity.answers")
    ToeicQuestionDTO convertToDTO(ToeicQuestion entity);

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "explanation", source = "dto.explanation")
    @Mapping(target = "answers", source = "dto.answers")
    void patchEntityFromDTO(ToeicQuestionDTO dto, @MappingTarget ToeicQuestion entity);

    @AfterMapping
    default void afterConvertToEntity(ToeicQuestionDTO dto, @MappingTarget ToeicQuestion entity) {
        if (entity.getAnswers() != null) {
            entity.getAnswers().forEach(answer -> answer.setQuestion(entity));
        }
    }

}
