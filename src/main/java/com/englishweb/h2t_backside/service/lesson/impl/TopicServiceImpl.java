package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.filter.VocabularyFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.LessonQuestionDTO;
import com.englishweb.h2t_backside.dto.lesson.ReadingDTO;
import com.englishweb.h2t_backside.dto.lesson.TopicDTO;
import com.englishweb.h2t_backside.dto.lesson.VocabularyDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.lesson.TopicMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.lesson.Topic;
import com.englishweb.h2t_backside.repository.lesson.TopicRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.lesson.LessonQuestionService;
import com.englishweb.h2t_backside.service.lesson.TopicService;
import com.englishweb.h2t_backside.service.lesson.VocabularyService;
import com.englishweb.h2t_backside.utils.LessonPagination;
import com.englishweb.h2t_backside.utils.LessonQuestionFinder;
import com.englishweb.h2t_backside.utils.ParseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class TopicServiceImpl extends BaseServiceImpl<TopicDTO, Topic, TopicRepository> implements TopicService {
    private final TopicMapper mapper;
    private final LessonQuestionService lessonQuestionService;
    private final VocabularyService vocabularyService;
    private static final String RESOURCE_NAME = "Topic";

    public TopicServiceImpl(TopicRepository repository, DiscordNotifier discordNotifier, @Lazy TopicMapper mapper, LessonQuestionService lessonQuestionService, VocabularyService vocabularyService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.lessonQuestionService = lessonQuestionService;
        this.vocabularyService = vocabularyService;
    }

    @Override
    public boolean delete(Long id) {
        // Delete other resources associated with the topic
        TopicDTO dto = super.findById(id);
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
    protected void createError(TopicDTO dto, Exception ex) {
        log.error("Error creating {}: {}", RESOURCE_NAME.toLowerCase(), ex.getMessage());
        String errorMessage = String.format("Unexpected error creating %s: %s", RESOURCE_NAME.toLowerCase(), ex.getMessage());
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;

        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(TopicDTO dto, Long id, Exception ex) {
        log.error("Error updating topic: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating topic: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)){
            errorMessage = String.format("Topic with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(TopicDTO dto, Topic entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected Topic convertToEntity(TopicDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected TopicDTO convertToDTO(Topic entity) {
        return mapper.convertToDTO(entity);
    }


    @Override
    public Page<TopicDTO> searchWithFilters(int page, int size, String sortFields, LessonFilterDTO filter) {
        return LessonPagination.searchWithFiltersGeneric(
                page, size, sortFields, filter, repository, Topic.class
        ).map(this::convertToDTO);
    }

    @Override
    public List<LessonQuestionDTO> findQuestionByLessonId(Long lessonId, Boolean status) {
        return LessonQuestionFinder.findQuestionsByLessonId(
                lessonId,
                status,
                RESOURCE_NAME,
                lessonQuestionService,
                TopicDTO::getQuestions, // Function to extract questions from DTO
                this::findById          // Function to find Topic by ID
        );
    }

    @Override
    public boolean verifyValidLesson(Long lessonId) {
        TopicDTO dto = super.findById(lessonId);
        if (dto.getQuestions().isEmpty())
            return false;

        // Check if at least one question is valid
        List<LessonQuestionDTO> questions = lessonQuestionService.findByIds(dto.getQuestions());
        if (questions.stream().allMatch(question -> !lessonQuestionService.verifyValidQuestion(question.getId()) || !question.getStatus())) {
            return false;
        }

        Page<VocabularyDTO> vocabularies = vocabularyService.searchWithFiltersTopicId(0, 10, "", null, lessonId);
        return vocabularies.getTotalElements() > 0
                && vocabularies.getContent().stream().anyMatch(AbstractBaseDTO::getStatus);
    }
}
