package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.lesson.LessonQuestionDTO;
import com.englishweb.h2t_backside.dto.lesson.ListeningDTO;
import com.englishweb.h2t_backside.dto.test.QuestionDTO;
import com.englishweb.h2t_backside.dto.test.TestListeningDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.TestListeningMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.TestListening;
import com.englishweb.h2t_backside.repository.test.TestListeningRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.QuestionService;
import com.englishweb.h2t_backside.service.test.TestListeningService;
import com.englishweb.h2t_backside.utils.LessonQuestionFinder;
import com.englishweb.h2t_backside.utils.QuestionFinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class TestListeningServiceImpl extends BaseServiceImpl<TestListeningDTO, TestListening, TestListeningRepository> implements TestListeningService {
    private final TestListeningMapper mapper;
    private final QuestionService questionService;
    private static final String RESOURCE_NAME = "TestListening";


    public TestListeningServiceImpl(TestListeningRepository repository, DiscordNotifier discordNotifier, TestListeningMapper mapper,@Lazy QuestionService questionService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.questionService = questionService;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("TestListening with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(TestListeningDTO dto, Exception ex) {
        log.error("Error creating test listening: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating test listening: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(TestListeningDTO dto, Long id, Exception ex) {
        log.error("Error updating test listening: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating test listening: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("TestListening with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }
    @Override
    public boolean delete(Long testListeningId) {
        TestListeningDTO dto = super.findById(testListeningId);


        if (dto.getQuestions() != null && !dto.getQuestions().isEmpty()) {
            for (Long questionId : dto.getQuestions()) {
                questionService.delete(questionId);
            }
        }


        return super.delete(testListeningId);
    }


    @Override
    protected void patchEntityFromDTO(TestListeningDTO dto, TestListening entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected TestListening convertToEntity(TestListeningDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected TestListeningDTO convertToDTO(TestListening entity) {
        return mapper.convertToDTO(entity);
    }
    @Override
    public List<TestListeningDTO> findByIds(List<Long> ids) {
        List<TestListeningDTO> result = new LinkedList<>();
        for (Long id : ids) {
            result.add(findById(id));
        }
        return result;
    }
    @Override
    public List<TestListeningDTO> findByIdsAndStatus(List<Long> ids, Boolean status) {
        if (status == null) {
            return repository.findAllById(ids)
                    .stream()
                    .map(this::convertToDTO).toList();
        }
        return repository.findByIdInAndStatus(ids, status)
                .stream()
                .map(this::convertToDTO).toList();
    }

    @Override
    public List<QuestionDTO> findQuestionByTestId(Long testId, Boolean status) {
        return QuestionFinder.findQuestionsByTestId(
                testId,
                status,
                RESOURCE_NAME,
                questionService,
                TestListeningDTO::getQuestions,
                this::findById
        );
    }
    @Override
    public boolean verifyValidTestListening(Long testListeningId) {
        TestListeningDTO dto = super.findById(testListeningId);

        if (dto.getAudio() == null || dto.getAudio().isEmpty()
                || dto.getTranscript() == null || dto.getTranscript().isEmpty()
                || dto.getQuestions() == null || dto.getQuestions().isEmpty() ) {
            return false;
        }

        List<QuestionDTO> questions = questionService.findByIds(dto.getQuestions());

        return questions.stream().anyMatch(q ->
                questionService.verifyValidQuestion(q.getId()) && q.getStatus()
        );
    }

}
