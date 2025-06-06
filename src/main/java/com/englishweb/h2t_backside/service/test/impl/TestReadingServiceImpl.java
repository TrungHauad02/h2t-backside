package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.QuestionDTO;
import com.englishweb.h2t_backside.dto.test.TestListeningDTO;
import com.englishweb.h2t_backside.dto.test.TestPartDTO;
import com.englishweb.h2t_backside.dto.test.TestReadingDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.TestReadingMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.TestReading;
import com.englishweb.h2t_backside.repository.test.TestReadingRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.QuestionService;
import com.englishweb.h2t_backside.service.test.TestReadingService;
import com.englishweb.h2t_backside.utils.ParseData;
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
public class TestReadingServiceImpl extends BaseServiceImpl<TestReadingDTO, TestReading, TestReadingRepository> implements TestReadingService {
    private final TestReadingMapper mapper;
    private final QuestionService questionService;
    private static final String RESOURCE_NAME = "TestReading";



    public TestReadingServiceImpl(TestReadingRepository repository, DiscordNotifier discordNotifier, TestReadingMapper mapper,@Lazy QuestionService questionService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.questionService = questionService;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("TestReading with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(TestReadingDTO dto, Exception ex) {
        log.error("Error creating test reading: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating test reading: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(TestReadingDTO dto, Long id, Exception ex) {
        log.error("Error updating test reading: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating test reading: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("TestReading with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(TestReadingDTO dto, TestReading entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }
    @Override
    public boolean delete(Long testReadingId) {
        TestReadingDTO dto = super.findById(testReadingId);

        if (dto.getQuestions() != null && !dto.getQuestions().isEmpty()) {
            for (Long questionId : dto.getQuestions()) {
                questionService.delete(questionId);
            }
        }

        return super.delete(testReadingId);
    }
    @Override
    protected TestReading convertToEntity(TestReadingDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected TestReadingDTO convertToDTO(TestReading entity) {
        return mapper.convertToDTO(entity);
    }
    @Override
    public List<TestReadingDTO> findByIds(List<Long> ids) {
        List<TestReadingDTO> result = new LinkedList<>();
        for (Long id : ids) {
            result.add(findById(id));
        }
        return result;
    }
    @Override
    public List<TestReadingDTO> findByIdsAndStatus(List<Long> ids, Boolean status) {
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
                TestReadingDTO::getQuestions,
                this::findById
        );
    }
    @Override
    public boolean verifyValidTestReading(Long testReadingId) {
        TestReadingDTO dto = super.findById(testReadingId);

        if (dto.getFile() == null || dto.getFile().isEmpty() ||
                dto.getQuestions() == null || dto.getQuestions().isEmpty() ) {
            return false;
        }

        List<QuestionDTO> questions = questionService.findByIds(dto.getQuestions());

        return questions.stream().anyMatch(q ->
                questionService.verifyValidQuestion(q.getId()) && q.getStatus()
        );
    }

}
