package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.AnswerDTO;
import com.englishweb.h2t_backside.model.test.Answer;
import com.englishweb.h2t_backside.model.test.Question;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface AnswerMapper {

    // Chuyển đổi từ AnswerDTO sang Answer Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "correct", source = "dto.correct")
    @Mapping(target = "question", source = "dto.questionId", qualifiedByName = "mapQuestionIdToQuestion")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    Answer convertToEntity(AnswerDTO dto);

    // Chuyển đổi từ Answer Entity sang AnswerDTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "content", source = "entity.content")
    @Mapping(target = "correct", source = "entity.correct")
    @Mapping(target = "questionId", source = "entity.question.id")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    AnswerDTO convertToDTO(Answer entity);

    // Cập nhật dữ liệu từ DTO vào Entity (chỉ cập nhật trường có giá trị)
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "correct", source = "dto.correct")
    @Mapping(target = "question", source = "dto.questionId", qualifiedByName = "mapQuestionIdToQuestion")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(AnswerDTO dto, @MappingTarget Answer entity);

    // Custom mapping methods
    @Named("mapQuestionIdToQuestion")
    default Question mapQuestionIdToQuestion(Long questionId) {
        if (questionId == null) return null;
        Question question = new Question();
        question.setId(questionId);
        return question;
    }
}
