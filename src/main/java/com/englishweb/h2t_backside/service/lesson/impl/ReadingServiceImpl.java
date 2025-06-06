package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.LessonQuestionDTO;
import com.englishweb.h2t_backside.dto.lesson.PreparationDTO;
import com.englishweb.h2t_backside.dto.lesson.ReadingDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.lesson.ReadingMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.lesson.Reading;
import com.englishweb.h2t_backside.repository.lesson.ReadingRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.lesson.LessonQuestionService;
import com.englishweb.h2t_backside.service.lesson.PreparationService;
import com.englishweb.h2t_backside.service.lesson.ReadingService;
import com.englishweb.h2t_backside.utils.LessonPagination;
import com.englishweb.h2t_backside.utils.LessonQuestionFinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReadingServiceImpl extends BaseServiceImpl<ReadingDTO, Reading, ReadingRepository> implements ReadingService {

    private final ReadingMapper mapper;
    private final LessonQuestionService lessonQuestionService;
    private final PreparationService preparationService;
    private static final String RESOURCE_NAME = "Reading";

    public ReadingServiceImpl(ReadingRepository repository, DiscordNotifier discordNotifier, @Lazy ReadingMapper mapper, LessonQuestionService lessonQuestionService, PreparationService preparationService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.lessonQuestionService = lessonQuestionService;
        this.preparationService = preparationService;
    }

    @Override
    public boolean delete(Long id) {
        // Delete other resources associated with the reading
        ReadingDTO dto = super.findById(id);
        lessonQuestionService.deleteAll(dto.getQuestions());
        return super.delete(id);
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format(RESOURCE_NAME + " with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(ReadingDTO dto, Exception ex) {
        log.error("Error creating " + RESOURCE_NAME.toLowerCase() + ": {}", ex.getMessage());
        String errorMessage = "Unexpected error creating " + RESOURCE_NAME.toLowerCase() + ": " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(ReadingDTO dto, Long id, Exception ex) {
        log.error("Error updating " + RESOURCE_NAME.toLowerCase() + ": {}", ex.getMessage());
        String errorMessage = "Unexpected error updating " + RESOURCE_NAME.toLowerCase() + ": " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format(RESOURCE_NAME + " with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(ReadingDTO dto, Reading entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected Reading convertToEntity(ReadingDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected ReadingDTO convertToDTO(Reading entity) {
        return mapper.convertToDTO(entity);
    }

    @Override
    public Page<ReadingDTO> searchWithFilters(int page, int size, String sortFields, LessonFilterDTO filter) {
        return LessonPagination.searchWithFiltersGeneric(
                page, size, sortFields, filter, repository, Reading.class
        ).map(this::convertToDTO);
    }

    @Override
    public List<LessonQuestionDTO> findQuestionByLessonId(Long lessonId, Boolean status) {
        return LessonQuestionFinder.findQuestionsByLessonId(
                lessonId,
                status,
                RESOURCE_NAME,
                lessonQuestionService,
                ReadingDTO::getQuestions, // Function to extract questions from DTO
                this::findById             // Function to find Reading by ID
        );
    }

    @Override
    public boolean verifyValidLesson(Long lessonId) {
        ReadingDTO dto = super.findById(lessonId);

        if (dto.getQuestions().isEmpty() ||
                dto.getPreparationId() == null) {
            return false;
        }

        // Check if at least one question is valid
        List<LessonQuestionDTO> questions = lessonQuestionService.findByIds(dto.getQuestions());
        if (questions.stream().allMatch(question -> !lessonQuestionService.verifyValidQuestion(question.getId()) || !question.getStatus())) {
            return false;
        }

        // Check if the preparation is valid
        PreparationDTO preparation = preparationService.findById(dto.getPreparationId());
        return preparation.getStatus() &&
                preparationService.verifyValidPreparation(dto.getPreparationId());
    }
}