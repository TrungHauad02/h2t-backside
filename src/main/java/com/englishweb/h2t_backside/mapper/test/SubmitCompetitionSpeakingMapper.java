package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitCompetitionSpeakingDTO;
import com.englishweb.h2t_backside.model.test.Question;
import com.englishweb.h2t_backside.model.test.SubmitCompetition;
import com.englishweb.h2t_backside.model.test.SubmitCompetitionSpeaking;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface SubmitCompetitionSpeakingMapper {

    // DTO → Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "submitCompetition", source = "dto.submitCompetition_id", qualifiedByName = "mapSubmitCompetitionId")
    @Mapping(target = "question", source = "dto.question_id", qualifiedByName = "mapQuestionId")
    @Mapping(target = "file", source = "dto.file", defaultValue = "")
    @Mapping(target = "transcript", source = "dto.transcript", defaultValue = "")
    @Mapping(target = "score", source = "dto.score")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    SubmitCompetitionSpeaking convertToEntity(SubmitCompetitionSpeakingDTO dto);

    // Entity → DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "submitCompetition_id", source = "entity.submitCompetition.id")
    @Mapping(target = "question_id", source = "entity.question.id")
    @Mapping(target = "file", source = "entity.file")
    @Mapping(target = "transcript", source = "entity.transcript")
    @Mapping(target = "score", source = "entity.score")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    SubmitCompetitionSpeakingDTO convertToDTO(SubmitCompetitionSpeaking entity);

    // Patch DTO → Entity
    @Mapping(target = "submitCompetition", source = "dto.submitCompetition_id", qualifiedByName = "mapSubmitCompetitionId")
    @Mapping(target = "file", source = "dto.file", defaultValue = "")
    @Mapping(target = "question", source = "dto.question_id", qualifiedByName = "mapQuestionId")
    @Mapping(target = "transcript", source = "dto.transcript")
    @Mapping(target = "score", source = "dto.score")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(SubmitCompetitionSpeakingDTO dto, @MappingTarget SubmitCompetitionSpeaking entity);


    @Named("mapSubmitCompetitionId")
    default SubmitCompetition mapSubmitCompetitionId(Long id) {
        if (id == null) return null;
        SubmitCompetition entity = new SubmitCompetition();
        entity.setId(id);
        return entity;
    }

    @Named("mapQuestionId")
    default Question mapQuestionId(Long id) {
        if (id == null) return null;
        Question entity = new Question();
        entity.setId(id);
        return entity;
    }
}
