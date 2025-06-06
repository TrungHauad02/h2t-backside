package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitCompetitionAnswerDTO;
import com.englishweb.h2t_backside.model.test.Answer;
import com.englishweb.h2t_backside.model.test.Question;
import com.englishweb.h2t_backside.model.test.SubmitCompetition;
import com.englishweb.h2t_backside.model.test.SubmitCompetitionAnswer;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface SubmitCompetitionAnswerMapper {

    // DTO → Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "submitCompetition", source = "dto.submitCompetition_id", qualifiedByName = "mapSubmitCompetitionId")
    @Mapping(target = "question", source = "dto.question_id", qualifiedByName = "mapQuestionId")
    @Mapping(target = "answer", source = "dto.answer_id", qualifiedByName = "mapAnswerId")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    SubmitCompetitionAnswer convertToEntity(SubmitCompetitionAnswerDTO dto);

    // Entity → DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "submitCompetition_id", source = "entity.submitCompetition.id")
    @Mapping(target = "question_id", source = "entity.question.id")
    @Mapping(target = "answer_id", source = "entity.answer.id")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    SubmitCompetitionAnswerDTO convertToDTO(SubmitCompetitionAnswer entity);


    @Mapping(target = "submitCompetition", source = "dto.submitCompetition_id", qualifiedByName = "mapSubmitCompetitionId")
    @Mapping(target = "question", source = "dto.question_id", qualifiedByName = "mapQuestionId")
    @Mapping(target = "answer", source = "dto.answer_id", qualifiedByName = "mapAnswerId")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(SubmitCompetitionAnswerDTO dto, @MappingTarget SubmitCompetitionAnswer entity);


    @Named("mapSubmitCompetitionId")
    static SubmitCompetition mapSubmitCompetitionId(Long id) {
        if (id == null) return null;
        SubmitCompetition entity = new SubmitCompetition();
        entity.setId(id);
        return entity;
    }

    @Named("mapQuestionId")
    default  Question mapQuestionId(Long id) {
        if (id == null) return null;
        Question entity = new Question();
        entity.setId(id);
        return entity;
    }

    @Named("mapAnswerId")
    default  Answer mapAnswerId(Long id) {
        if (id == null) return null;
        Answer entity = new Answer();
        entity.setId(id);
        return entity;
    }
}
