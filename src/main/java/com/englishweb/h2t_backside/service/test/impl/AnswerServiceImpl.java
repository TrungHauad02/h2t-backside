package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.AnswerDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.AnswerMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.Answer;
import com.englishweb.h2t_backside.repository.test.AnswerRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.AnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AnswerServiceImpl extends BaseServiceImpl<AnswerDTO, Answer, AnswerRepository> implements AnswerService {
    private final AnswerMapper mapper;

    public AnswerServiceImpl(AnswerRepository repository, DiscordNotifier discordNotifier, AnswerMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("Answer with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(AnswerDTO dto, Exception ex) {
        log.error("Error creating answer: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating answer: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(AnswerDTO dto, Long id, Exception ex) {
        log.error("Error updating answer: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating answer: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("Answer with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    public List<AnswerDTO> findByQuestionId(Long questionId) {
        List<Answer> answers = repository.findByQuestionId(questionId);
        return answers.stream().map(mapper::convertToDTO).toList();
    }

    @Override
    protected void patchEntityFromDTO(AnswerDTO dto, Answer entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected Answer convertToEntity(AnswerDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected AnswerDTO convertToDTO(Answer entity) {
        return mapper.convertToDTO(entity);
    }
}
