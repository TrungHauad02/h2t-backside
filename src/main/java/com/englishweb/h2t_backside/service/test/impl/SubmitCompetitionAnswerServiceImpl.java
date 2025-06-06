package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.SubmitCompetitionAnswerDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.SubmitCompetitionAnswerMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.SubmitCompetitionAnswer;
import com.englishweb.h2t_backside.repository.test.SubmitCompetitionAnswerRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.SubmitCompetitionAnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SubmitCompetitionAnswerServiceImpl extends BaseServiceImpl<SubmitCompetitionAnswerDTO, SubmitCompetitionAnswer, SubmitCompetitionAnswerRepository> implements SubmitCompetitionAnswerService {
    private final SubmitCompetitionAnswerMapper mapper;

    public SubmitCompetitionAnswerServiceImpl(SubmitCompetitionAnswerRepository repository, DiscordNotifier discordNotifier, SubmitCompetitionAnswerMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("SubmitCompetitionAnswer with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }
    @Override
    public List<SubmitCompetitionAnswerDTO> findBySubmitCompetitionIdAndQuestionId(Long submitCompetitionId, Long questionId) {
        return repository.findBySubmitCompetitionIdAndQuestionId(submitCompetitionId, questionId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<SubmitCompetitionAnswerDTO> findBySubmitCompetitionIdAndQuestionIds(Long submitCompetitionId, List<Long> questionIds) {
        return repository.findBySubmitCompetitionIdAndQuestionIdIn(submitCompetitionId, questionIds)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }


    @Override
    public List<SubmitCompetitionAnswerDTO> findBySubmitCompetitionId(Long submitCompetitionId) {
        return repository.findBySubmitCompetitionId(submitCompetitionId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }


    @Override
    protected void createError(SubmitCompetitionAnswerDTO dto, Exception ex) {
        log.error("Error creating submit competition answer: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating submit competition answer: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(SubmitCompetitionAnswerDTO dto, Long id, Exception ex) {
        log.error("Error updating submit competition answer: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating submit competition answer: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("SubmitCompetitionAnswer with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(SubmitCompetitionAnswerDTO dto, SubmitCompetitionAnswer entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected SubmitCompetitionAnswer convertToEntity(SubmitCompetitionAnswerDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected SubmitCompetitionAnswerDTO convertToDTO(SubmitCompetitionAnswer entity) {
        return mapper.convertToDTO(entity);
    }
}
