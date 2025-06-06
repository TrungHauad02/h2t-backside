package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.SubmitTestAnswerDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.SubmitTestAnswerMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.SubmitTestAnswer;
import com.englishweb.h2t_backside.repository.test.SubmitTestAnswerRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.SubmitTestAnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SubmitTestAnswerServiceImpl extends BaseServiceImpl<SubmitTestAnswerDTO, SubmitTestAnswer, SubmitTestAnswerRepository> implements SubmitTestAnswerService {
    private final SubmitTestAnswerMapper mapper;

    public SubmitTestAnswerServiceImpl(SubmitTestAnswerRepository repository, DiscordNotifier discordNotifier, SubmitTestAnswerMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("SubmitTestAnswer with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }
    @Override
    public List<SubmitTestAnswerDTO> findBySubmitTestIdAndQuestionId(Long submitTestId, Long questionId) {
        return repository.findBySubmitTestIdAndQuestionId(submitTestId, questionId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<SubmitTestAnswerDTO> findBySubmitTestId(Long submitTestId) {
        return repository.findBySubmitTestId(submitTestId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<SubmitTestAnswerDTO> findBySubmitTestIdAndQuestionIds(Long submitTestId, List<Long> questionIds) {
        return repository.findBySubmitTestIdAndQuestionIdIn(submitTestId, questionIds)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    protected void createError(SubmitTestAnswerDTO dto, Exception ex) {
        log.error("Error creating submit test answer: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating submit test answer: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(SubmitTestAnswerDTO dto, Long id, Exception ex) {
        log.error("Error updating submit test answer: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating submit test answer: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("SubmitTestAnswer with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.HIGH);
    }

    @Override
    protected void patchEntityFromDTO(SubmitTestAnswerDTO dto, SubmitTestAnswer entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected SubmitTestAnswer convertToEntity(SubmitTestAnswerDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected SubmitTestAnswerDTO convertToDTO(SubmitTestAnswer entity) {
        return mapper.convertToDTO(entity);
    }
}
