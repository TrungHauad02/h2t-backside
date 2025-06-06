package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.QuestionDTO;
import com.englishweb.h2t_backside.dto.test.TestListeningDTO;
import com.englishweb.h2t_backside.dto.test.TestReadingDTO;
import com.englishweb.h2t_backside.dto.test.TestSpeakingDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.TestSpeakingMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.TestSpeaking;
import com.englishweb.h2t_backside.repository.test.TestSpeakingRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.QuestionService;
import com.englishweb.h2t_backside.service.test.TestSpeakingService;
import com.englishweb.h2t_backside.utils.QuestionFinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TestSpeakingServiceImpl extends BaseServiceImpl<TestSpeakingDTO, TestSpeaking, TestSpeakingRepository> implements TestSpeakingService {
    private final TestSpeakingMapper mapper;
    private final QuestionService questionService;
    private static final String RESOURCE_NAME = "TestSpeaking";

    public TestSpeakingServiceImpl(TestSpeakingRepository repository, DiscordNotifier discordNotifier, TestSpeakingMapper mapper,@Lazy QuestionService questionService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.questionService = questionService;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("TestSpeaking with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(TestSpeakingDTO dto, Exception ex) {
        log.error("Error creating test speaking: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating test speaking: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(TestSpeakingDTO dto, Long id, Exception ex) {
        log.error("Error updating test speaking: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating test speaking: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("TestSpeaking with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    public boolean delete(Long testSpeakingId) {
        TestSpeakingDTO dto = super.findById(testSpeakingId);

        if (dto.getQuestions() != null && !dto.getQuestions().isEmpty()) {
            for (Long questionId : dto.getQuestions()) {
                questionService.delete(questionId);
            }
        }

        return super.delete(testSpeakingId);
    }



    @Override
    protected void patchEntityFromDTO(TestSpeakingDTO dto, TestSpeaking entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected TestSpeaking convertToEntity(TestSpeakingDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected TestSpeakingDTO convertToDTO(TestSpeaking entity) {
        return mapper.convertToDTO(entity);
    }
    @Override
    public List<TestSpeakingDTO> findByIds(List<Long> ids) {
        List<TestSpeakingDTO> result = new LinkedList<>();
        for (Long id : ids) {
            result.add(findById(id));
        }
        return result;
    }
    public List<TestSpeakingDTO> findByIdsAndStatus(List<Long> ids, Boolean status) {
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
                TestSpeakingDTO::getQuestions,
                this::findById
        );
    }
    @Override
    public boolean verifyValidTestSpeaking(Long testSpeakingId) {
        TestSpeakingDTO dto = super.findById(testSpeakingId);

        if (dto.getQuestions() == null || dto.getQuestions().isEmpty()
                || dto.getTitle() == null || dto.getTitle().isEmpty()) return false;

        List<QuestionDTO> questions = questionService.findByIds(dto.getQuestions());

        return questions.stream().anyMatch(q ->
                q.getContent() != null && !q.getContent().isEmpty() && q.getStatus()
        );
    }

}
