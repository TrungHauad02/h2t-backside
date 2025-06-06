package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.lesson.LessonAnswerDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.lesson.LessonAnswerMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.lesson.LessonAnswer;
import com.englishweb.h2t_backside.repository.lesson.LessonAnswerRepository;
import com.englishweb.h2t_backside.service.feature.impl.DiscordNotifierImpl;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.lesson.LessonAnswerService;
import org.springframework.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LessonAnswerServiceImpl extends BaseServiceImpl<LessonAnswerDTO, LessonAnswer, LessonAnswerRepository> implements LessonAnswerService {

    private final LessonAnswerMapper mapper;

    public LessonAnswerServiceImpl(LessonAnswerRepository repository, DiscordNotifierImpl discordNotifier, LessonAnswerMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("LessonAnswer with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(LessonAnswerDTO dto, Exception ex) {
        log.error("Error creating LessonAnswer: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating LessonAnswer: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_ANSWER_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(LessonAnswerDTO dto, Long id, Exception ex) {
        log.error("Error updating LessonAnswer: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating LessonAnswer: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_ANSWER_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("LessonAnswer with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(LessonAnswerDTO dto, LessonAnswer entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected LessonAnswer convertToEntity(LessonAnswerDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected LessonAnswerDTO convertToDTO(LessonAnswer entity) {
        return mapper.convertToDTO(entity);
    }
}