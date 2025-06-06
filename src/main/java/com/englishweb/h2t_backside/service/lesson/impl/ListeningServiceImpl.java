package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.LessonQuestionDTO;
import com.englishweb.h2t_backside.dto.lesson.ListenAndWriteAWordDTO;
import com.englishweb.h2t_backside.dto.lesson.ListeningDTO;
import com.englishweb.h2t_backside.dto.lesson.PreparationDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.lesson.ListeningMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.lesson.Listening;
import com.englishweb.h2t_backside.repository.lesson.ListeningRepository;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.feature.impl.DiscordNotifierImpl;
import com.englishweb.h2t_backside.service.lesson.LessonQuestionService;
import com.englishweb.h2t_backside.service.lesson.ListenAndWriteAWordService;
import com.englishweb.h2t_backside.service.lesson.ListeningService;
import com.englishweb.h2t_backside.service.lesson.PreparationService;
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
public class ListeningServiceImpl extends BaseServiceImpl<ListeningDTO, Listening, ListeningRepository> implements ListeningService {

    private final ListeningMapper mapper;
    private final LessonQuestionService lessonQuestionService;
    private final ListenAndWriteAWordService listenAndWriteAWordService;
    private final PreparationService preparationService;
    private static final String RESOURCE_NAME = "Listening";

    public ListeningServiceImpl(ListeningRepository repository,
                                DiscordNotifierImpl discordNotifier,
                                @Lazy ListeningMapper mapper,
                                LessonQuestionService lessonQuestionService,
                                @Lazy ListenAndWriteAWordService listenAndWriteAWordService,
                                PreparationService preparationService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.lessonQuestionService = lessonQuestionService;
        this.listenAndWriteAWordService = listenAndWriteAWordService;
        this.preparationService = preparationService;
    }

    @Override
    public boolean delete(Long id) {
        // Delete other resources associated with the listening
        ListeningDTO dto = super.findById(id);
        lessonQuestionService.deleteAll(dto.getQuestions());
        List<ListenAndWriteAWordDTO> listDTO = listenAndWriteAWordService.findByListeningId(id);
        listenAndWriteAWordService.deleteAll(listDTO.stream().map(ListenAndWriteAWordDTO::getId).toList());
        return super.delete(id);
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format(RESOURCE_NAME + " with ID '%d' not found.", id);
        log.warn(errorMessage);

        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(ListeningDTO dto, Exception ex) {
        log.error("Error creating " + RESOURCE_NAME.toLowerCase() + ": {}", ex.getMessage());
        String errorMessage = "Unexpected error creating " + RESOURCE_NAME.toLowerCase() + ": " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;

        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(ListeningDTO dto, Long id, Exception ex) {
        log.error("Error updating " + RESOURCE_NAME.toLowerCase() + ": {}", ex.getMessage());
        String errorMessage = "Unexpected error updating " + RESOURCE_NAME.toLowerCase() + ": " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)){
            errorMessage = String.format(RESOURCE_NAME + " with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(ListeningDTO dto, Listening entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected Listening convertToEntity(ListeningDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected ListeningDTO convertToDTO(Listening entity) {
        return mapper.convertToDTO(entity);
    }

    @Override
    public Page<ListeningDTO> searchWithFilters(int page, int size, String sortFields, LessonFilterDTO filter) {
        return LessonPagination.searchWithFiltersGeneric(
                page, size, sortFields, filter, repository, Listening.class
        ).map(this::convertToDTO);
    }

    @Override
    public List<LessonQuestionDTO> findQuestionByLessonId(Long lessonId, Boolean status) {
        return LessonQuestionFinder.findQuestionsByLessonId(
                lessonId,
                status,
                RESOURCE_NAME,
                lessonQuestionService,
                ListeningDTO::getQuestions, // Function to extract questions from DTO
                this::findById             // Function to find Listening by ID
        );
    }

    @Override
    public boolean verifyValidLesson(Long lessonId) {
        ListeningDTO dto = super.findById(lessonId);
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
        if (!preparation.getStatus() ||
                !preparationService.verifyValidPreparation(dto.getPreparationId())) {
            return false;
        }

        // Check if there are listenAndWriteAWords
        List<ListenAndWriteAWordDTO> listenAndWriteAWords = listenAndWriteAWordService.findByListeningId(lessonId);
        if (listenAndWriteAWords.isEmpty()) {
            return false;
        }

        // Check if at least one listenAndWriteAWord is active
        return listenAndWriteAWords.stream().anyMatch(ListenAndWriteAWordDTO::getStatus);
    }
}