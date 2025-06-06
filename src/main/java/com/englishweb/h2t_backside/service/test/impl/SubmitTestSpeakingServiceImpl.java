package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.SubmitTestSpeakingDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.SubmitTestSpeakingMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.SubmitTestSpeaking;
import com.englishweb.h2t_backside.repository.test.SubmitTestSpeakingRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.SubmitTestSpeakingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SubmitTestSpeakingServiceImpl extends BaseServiceImpl<SubmitTestSpeakingDTO, SubmitTestSpeaking, SubmitTestSpeakingRepository> implements SubmitTestSpeakingService {
    private final SubmitTestSpeakingMapper mapper;

    public SubmitTestSpeakingServiceImpl(SubmitTestSpeakingRepository repository, DiscordNotifier discordNotifier, SubmitTestSpeakingMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }
    @Override
    public List<SubmitTestSpeakingDTO> findBySubmitTestIdAndQuestionId(Long submitTestId, Long questionId) {
        return repository.findBySubmitTestIdAndQuestionId(submitTestId, questionId).stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<SubmitTestSpeakingDTO> findBySubmitTestIdAndQuestionIds(Long submitTestId, List<Long> questionIds) {
        return repository.findBySubmitTestIdAndQuestionIdIn(submitTestId, questionIds).stream()
                .map(this::convertToDTO)
                .toList();
    }
    @Override
    public List<SubmitTestSpeakingDTO> findBySubmitTestId(Long submitTestId) {
        return repository.findBySubmitTestId(submitTestId).stream()
                .map(this::convertToDTO)
                .toList();
    }


    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("SubmitTestSpeaking with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(SubmitTestSpeakingDTO dto, Exception ex) {
        log.error("Error creating submit test speaking: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating submit test speaking: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(SubmitTestSpeakingDTO dto, Long id, Exception ex) {
        log.error("Error updating submit test speaking: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating submit test speaking: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("SubmitTestSpeaking with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(SubmitTestSpeakingDTO dto, SubmitTestSpeaking entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected SubmitTestSpeaking convertToEntity(SubmitTestSpeakingDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected SubmitTestSpeakingDTO convertToDTO(SubmitTestSpeaking entity) {
        return mapper.convertToDTO(entity);
    }
}
