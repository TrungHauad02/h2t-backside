package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.lesson.WritingAnswerDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.lesson.WritingAnswerMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.lesson.WritingAnswer;
import com.englishweb.h2t_backside.repository.lesson.WritingAnswerRepository;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.feature.impl.DiscordNotifierImpl;
import com.englishweb.h2t_backside.service.lesson.WritingAnswerService;
import com.englishweb.h2t_backside.service.lesson.WritingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WritingAnswerServiceImpl
        extends BaseServiceImpl<WritingAnswerDTO, WritingAnswer, WritingAnswerRepository>
        implements WritingAnswerService {

    private final WritingAnswerMapper mapper;
    private final WritingService writingService;

    public WritingAnswerServiceImpl(WritingAnswerRepository repository,
                                    DiscordNotifierImpl discordNotifier,
                                    WritingAnswerMapper mapper, WritingService writingService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.writingService = writingService;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("WritingAnswer with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(WritingAnswerDTO dto, Exception ex) {
        log.error("Error creating WritingAnswer: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating WritingAnswer: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(WritingAnswerDTO dto, Long id, Exception ex) {
        log.error("Error updating WritingAnswer: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating WritingAnswer: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("WritingAnswer with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(WritingAnswerDTO dto, WritingAnswer entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected WritingAnswer convertToEntity(WritingAnswerDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected WritingAnswerDTO convertToDTO(WritingAnswer entity) {
        return mapper.convertToDTO(entity);
    }

    @Override
    public List<WritingAnswerDTO> findByWritingId(Long writingId) {
        if (!writingService.isExist(writingId)) {
            throw new ResourceNotFoundException(writingId, String.format("Writing with ID '%d' not found.", writingId), SeverityEnum.LOW);
        }
        return repository.findByWriting_Id(writingId).stream().map(this::convertToDTO).toList();
    }
}
