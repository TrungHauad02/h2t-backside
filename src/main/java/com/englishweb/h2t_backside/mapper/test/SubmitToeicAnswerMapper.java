package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitToeicAnswerDTO;
import com.englishweb.h2t_backside.model.test.SubmitToeicAnswer;
import com.englishweb.h2t_backside.model.test.SubmitToeic;
import com.englishweb.h2t_backside.model.test.ToeicQuestion;
import com.englishweb.h2t_backside.model.test.ToeicAnswer;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface SubmitToeicAnswerMapper {

    @Mapping(target = "submitToeic.id", source = "submitToeicId")
    @Mapping(target = "question.id", source = "toeicQuestionId")
    @Mapping(target = "answer.id", source = "toeicAnswerId")
    SubmitToeicAnswer convertToEntity(SubmitToeicAnswerDTO dto);

    @Mapping(target = "submitToeicId", source = "submitToeic.id")
    @Mapping(target = "toeicQuestionId", source = "question.id")
    @Mapping(target = "toeicAnswerId", source = "answer.id")
    SubmitToeicAnswerDTO convertToDTO(SubmitToeicAnswer entity);

    @Mapping(target = "submitToeic.id", source = "submitToeicId")
    @Mapping(target = "question.id", source = "toeicQuestionId")
    @Mapping(target = "answer.id", source = "toeicAnswerId")
    void patchEntityFromDTO(SubmitToeicAnswerDTO dto, @MappingTarget SubmitToeicAnswer entity);
}
