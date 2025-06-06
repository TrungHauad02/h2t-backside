package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.SubmitCompetitionSpeakingDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.SubmitCompetitionSpeakingMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.SubmitCompetitionSpeaking;
import com.englishweb.h2t_backside.repository.test.SubmitCompetitionSpeakingRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.SubmitCompetitionSpeakingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SubmitCompetitionSpeakingServiceImpl extends BaseServiceImpl<SubmitCompetitionSpeakingDTO, SubmitCompetitionSpeaking, SubmitCompetitionSpeakingRepository> implements SubmitCompetitionSpeakingService {
    private final SubmitCompetitionSpeakingMapper mapper;

    public SubmitCompetitionSpeakingServiceImpl(SubmitCompetitionSpeakingRepository repository, DiscordNotifier discordNotifier, SubmitCompetitionSpeakingMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("SubmitCompetitionSpeaking with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }
    @Override
    public List<SubmitCompetitionSpeakingDTO> findBySubmitCompetitionIdAndQuestionId(Long submitCompetitionId, Long questionId) {
        return repository.findBySubmitCompetitionIdAndQuestionId(submitCompetitionId, questionId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<SubmitCompetitionSpeakingDTO> findBySubmitCompetitionIdAndQuestionIds(Long submitCompetitionId, List<Long> questionIds) {
        return repository.findBySubmitCompetitionIdAndQuestionIdIn(submitCompetitionId, questionIds)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<SubmitCompetitionSpeakingDTO> findBySubmitCompetitionId(Long submitCompetitionId ) {
        return repository.findBySubmitCompetitionId(submitCompetitionId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    protected void createError(SubmitCompetitionSpeakingDTO dto, Exception ex) {
        log.error("Error creating submit competition speaking: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating submit competition speaking: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(SubmitCompetitionSpeakingDTO dto, Long id, Exception ex) {
        log.error("Error updating submit competition speaking: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating submit competition speaking: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("SubmitCompetitionSpeaking with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(SubmitCompetitionSpeakingDTO dto, SubmitCompetitionSpeaking entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected SubmitCompetitionSpeaking convertToEntity(SubmitCompetitionSpeakingDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected SubmitCompetitionSpeakingDTO convertToDTO(SubmitCompetitionSpeaking entity) {
        return mapper.convertToDTO(entity);
    }
}
