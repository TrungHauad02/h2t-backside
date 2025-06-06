package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.PreparationDTO;
import com.englishweb.h2t_backside.dto.lesson.WritingAnswerDTO;
import com.englishweb.h2t_backside.dto.lesson.WritingDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.lesson.WritingMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.lesson.Writing;
import com.englishweb.h2t_backside.repository.lesson.WritingRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.lesson.PreparationService;
import com.englishweb.h2t_backside.service.lesson.WritingAnswerService;
import com.englishweb.h2t_backside.service.lesson.WritingService;
import com.englishweb.h2t_backside.utils.LessonPagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WritingServiceImpl extends BaseServiceImpl<WritingDTO, Writing, WritingRepository> implements WritingService {

    private final WritingMapper mapper;
    private final PreparationService preparationService;
    private final WritingAnswerService writingAnswerService;

    public WritingServiceImpl(WritingRepository repository, DiscordNotifier discordNotifier,
                              @Lazy WritingMapper mapper, PreparationService preparationService,
                              @Lazy WritingAnswerService writingAnswerService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.preparationService = preparationService;
        this.writingAnswerService = writingAnswerService;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("Writing with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(WritingDTO dto, Exception ex) {
        log.error("Error creating writing: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating writing: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(WritingDTO dto, Long id, Exception ex) {
        log.error("Error updating writing: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating writing: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("Writing with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(WritingDTO dto, Writing entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected Writing convertToEntity(WritingDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected WritingDTO convertToDTO(Writing entity) {
        return mapper.convertToDTO(entity);
    }

    @Override
    public Page<WritingDTO> searchWithFilters(int page, int size, String sortFields, LessonFilterDTO filter) {
        return LessonPagination.searchWithFiltersGeneric(
                page, size, sortFields, filter, repository, Writing.class
        ).map(this::convertToDTO);
    }

    @Override
    public boolean verifyValidLesson(Long lessonId) {
        WritingDTO dto = super.findById(lessonId);

        if (dto.getTips().isEmpty() ||
                dto.getPreparationId() == null) {
            return false;
        }

        // Check if the preparation is valid
        PreparationDTO preparation = preparationService.findById(dto.getPreparationId());
        if (!preparation.getStatus() ||
                !preparationService.verifyValidPreparation(dto.getPreparationId())) {
            return false;
        }

        List<WritingAnswerDTO> writingAnswers = writingAnswerService.findByWritingId(lessonId);
        return !writingAnswers.isEmpty()
                && writingAnswers.stream().anyMatch(WritingAnswerDTO::getStatus);
    }
}