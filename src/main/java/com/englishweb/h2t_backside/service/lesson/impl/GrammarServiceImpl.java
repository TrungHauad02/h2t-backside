package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.GrammarDTO;
import com.englishweb.h2t_backside.dto.lesson.LessonQuestionDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.lesson.GrammarMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.lesson.Grammar;
import com.englishweb.h2t_backside.repository.lesson.GrammarRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.lesson.GrammarService;
import com.englishweb.h2t_backside.service.lesson.LessonQuestionService;
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
public class GrammarServiceImpl extends BaseServiceImpl<GrammarDTO, Grammar, GrammarRepository> implements GrammarService {
    private final GrammarMapper mapper;
    private final LessonQuestionService lessonQuestionService;
    private static final String RESOURCE_NAME = "Grammar";

    public GrammarServiceImpl(GrammarRepository repository, DiscordNotifier discordNotifier, @Lazy GrammarMapper mapper, LessonQuestionService lessonQuestionService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.lessonQuestionService = lessonQuestionService;
    }

    @Override
    public boolean delete(Long id) {
        // Delete other resources associated with the grammar
        GrammarDTO dto = super.findById(id);
        lessonQuestionService.deleteAll(dto.getQuestions());
        return super.delete(id);
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("%s with ID '%d' not found.", RESOURCE_NAME, id);
        log.warn(errorMessage);

        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(GrammarDTO dto, Exception ex) {
        log.error("Error creating {}: {}", RESOURCE_NAME.toLowerCase(), ex.getMessage());
        String errorMessage = String.format("Unexpected error creating %s: %s", RESOURCE_NAME.toLowerCase(), ex.getMessage());
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;

        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(GrammarDTO dto, Long id, Exception ex) {
        log.error("Error updating {}: {}", RESOURCE_NAME.toLowerCase(), ex.getMessage());
        String errorMessage = String.format("Unexpected error updating %s: %s", RESOURCE_NAME.toLowerCase(), ex.getMessage());
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)){
            errorMessage = String.format("%s with ID '%d' not found.", RESOURCE_NAME, id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(GrammarDTO dto, Grammar entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected Grammar convertToEntity(GrammarDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected GrammarDTO convertToDTO(Grammar entity) {
        return mapper.convertToDTO(entity);
    }

    @Override
    public Page<GrammarDTO> searchWithFilters(int page, int size, String sortFields, LessonFilterDTO filter) {
        return LessonPagination.searchWithFiltersGeneric(
                page, size, sortFields, filter, repository, Grammar.class
        ).map(this::convertToDTO);
    }

    @Override
    public List<LessonQuestionDTO> findQuestionByLessonId(Long lessonId, Boolean status) {
        return LessonQuestionFinder.findQuestionsByLessonId(
                lessonId,
                status,
                RESOURCE_NAME,
                lessonQuestionService,
                GrammarDTO::getQuestions, // Function to extract questions from DTO
                this::findById             // Function to find Grammar by ID
        );
    }

    @Override
    public boolean verifyValidLesson(Long lessonId) {
        GrammarDTO dto = super.findById(lessonId);
        if (dto.getTips().isEmpty() || dto.getQuestions().isEmpty())
            return false;
        List<LessonQuestionDTO> questions = lessonQuestionService.findByIds(dto.getQuestions());
        // Check if at least one question is valid and active
        return questions.stream().anyMatch((question) -> lessonQuestionService.verifyValidQuestion(question.getId()) && question.getStatus());
    }
}