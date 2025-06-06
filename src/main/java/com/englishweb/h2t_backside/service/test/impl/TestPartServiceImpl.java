package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.*;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.TestPartMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.enummodel.TestPartEnum;
import com.englishweb.h2t_backside.model.test.Test;
import com.englishweb.h2t_backside.model.test.TestPart;
import com.englishweb.h2t_backside.repository.test.TestPartRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.*;
import com.englishweb.h2t_backside.utils.QuestionFinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class TestPartServiceImpl extends BaseServiceImpl<TestPartDTO, TestPart, TestPartRepository> implements TestPartService {

    private final TestReadingService testReadingService;
    private final TestListeningService testListeningService;
    private final TestSpeakingService testSpeakingService;
    private final TestPartMapper mapper;
    private final QuestionService questionService;
    private final TestWritingService testWritingService;
    private static final String RESOURCE_NAME = "TestPart";

    public TestPartServiceImpl(TestPartRepository repository,
                               DiscordNotifier discordNotifier,
                               TestPartMapper mapper,
                               @Lazy TestReadingService testReadingService,
                               @Lazy TestListeningService testListeningService,
                               @Lazy TestSpeakingService testSpeakingService,@Lazy  QuestionService questionService,@Lazy TestWritingService testWritingService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.testReadingService = testReadingService;
        this.testListeningService = testListeningService;
        this.testSpeakingService = testSpeakingService;
        this.questionService = questionService;
        this.testWritingService = testWritingService;
    }


    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("TestPart with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(TestPartDTO dto, Exception ex) {
        log.error("Error creating test part: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating test part: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(TestPartDTO dto, Long id, Exception ex) {
        log.error("Error updating test part: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating test part: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("TestPart with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }
    @Override
    public boolean delete(Long testPartId) {
        TestPartDTO part = super.findById(testPartId);

        if (part.getQuestions() != null && part.getType() != null) {
            List<Long> ids = part.getQuestions();
            TestPartEnum type = part.getType();

            switch (type) {
                case VOCABULARY:
                case GRAMMAR:
                    for (Long id : ids) {
                        questionService.delete(id);
                    }
                    break;
                case READING:
                    for (Long id : ids) {
                        testReadingService.delete(id);
                    }
                    break;
                case LISTENING:
                    for (Long id : ids) {
                        testListeningService.delete(id);
                    }
                    break;
                case SPEAKING:
                    for (Long id : ids) {
                        testSpeakingService.delete(id);
                    }
                    break;
                case WRITING:
                    for (Long id : ids) {
                        testWritingService.delete(id);
                    }
                    break;
            }
        }

        return super.delete(testPartId);
    }


    @Override
    protected void patchEntityFromDTO(TestPartDTO dto, TestPart entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected TestPart convertToEntity(TestPartDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected TestPartDTO convertToDTO(TestPart entity) {
        return mapper.convertToDTO(entity);
    }

    @Override
    public int countTotalQuestionsOfTest(List<Long> testParts, Boolean status) {
        if (testParts == null || testParts.isEmpty()) return 0;

        return testParts.stream()
                .mapToInt(partId -> {
                    try {
                        TestPartDTO part = this.findById(partId);
                        List<Long> questionIds = part.getQuestions();

                        return switch (part.getType()) {
                            case VOCABULARY, GRAMMAR -> {
                                List<Long> validQuestions = questionService
                                        .findByIdsAndStatus(questionIds, status)
                                        .stream()
                                        .map(q -> q.getId())
                                        .toList();
                                yield validQuestions.size();
                            }

                            case WRITING -> {
                                List<TestWritingDTO> writings = testWritingService.findByIdsAndStatus(questionIds, status);
                                yield writings.size();
                            }

                            case LISTENING -> countSubTestQuestionsByStatus(
                                    questionIds,
                                    status,
                                    "listening"
                            );

                            case READING -> countSubTestQuestionsByStatus(
                                    questionIds,
                                    status,
                                    "reading"
                            );

                            case SPEAKING -> countSubTestQuestionsByStatus(
                                    questionIds,
                                    status,
                                    "speaking"
                            );

                            default -> 0;
                        };

                    } catch (Exception e) {
                        log.warn("Error with TestPart ID {}: {}", partId, e.getMessage());
                        return 0;
                    }
                })
                .sum();
    }


    private int countSubTestQuestionsByStatus(List<Long> testIds, Boolean status, String type) {
        if (testIds == null || testIds.isEmpty()) return 0;

        List<Long> allQuestionIds = new ArrayList<>();

        switch (type) {
            case "reading" -> {
                List<TestReadingDTO> readings = testReadingService.findByIdsAndStatus(testIds, status);
                for (TestReadingDTO r : readings) {
                    if (r.getQuestions() != null) {
                        allQuestionIds.addAll(r.getQuestions());
                    }
                }
            }
            case "listening" -> {
                List<TestListeningDTO> listenings = testListeningService.findByIdsAndStatus(testIds, status);
                for (TestListeningDTO l : listenings) {
                    if (l.getQuestions() != null) {
                        allQuestionIds.addAll(l.getQuestions());
                    }
                }
            }
            case "speaking" -> {
                List<TestSpeakingDTO> speakings = testSpeakingService.findByIdsAndStatus(testIds, status);
                for (TestSpeakingDTO s : speakings) {
                    if (s.getQuestions() != null) {
                        allQuestionIds.addAll(s.getQuestions());
                    }
                }
            }
        }

        if (allQuestionIds.isEmpty()) return 0;

        // Chỉ đếm những câu hỏi có status = true
        return questionService.findByIdsAndStatus(allQuestionIds, status).size();
    }

    @Override
    public List<TestPartDTO> findByIds(List<Long> ids) {
        List<TestPartDTO> result = new LinkedList<>();
        for (Long id : ids) {
            result.add(findById(id));
        }
        return result;
    }
    @Override
    public List<QuestionDTO> findQuestionByTestId(Long testId, Boolean status) {
        return QuestionFinder.findQuestionsByTestId(
                testId,
                status,
                RESOURCE_NAME,
                questionService,
                TestPartDTO::getQuestions,
                this::findById
        );
    }
    @Override
    public boolean verifyValidTestPart(Long testPartId) {
        TestPartDTO part = super.findById(testPartId);

        if (part.getQuestions() == null || part.getQuestions().isEmpty() || part.getType() == null) {
            return false;
        }

        List<Long> ids = part.getQuestions();
        TestPartEnum type = part.getType();

        return switch (type) {
            case VOCABULARY, GRAMMAR -> {
                List<QuestionDTO> questions = questionService.findByIds(ids);
                yield questions.stream().anyMatch(q -> questionService.verifyValidQuestion(q.getId()) && q.getStatus());
            }
            case READING -> ids.stream()
                    .map(testReadingService::findById)
                    .filter(TestReadingDTO::getStatus)
                    .anyMatch(dto -> testReadingService.verifyValidTestReading(dto.getId()));
            case LISTENING -> ids.stream()
                    .map(testListeningService::findById)
                    .filter(TestListeningDTO::getStatus)
                    .anyMatch(dto -> testListeningService.verifyValidTestListening(dto.getId()));
            case SPEAKING -> ids.stream()
                    .map(testSpeakingService::findById)
                    .filter(TestSpeakingDTO::getStatus)
                    .anyMatch(dto -> testSpeakingService.verifyValidTestSpeaking(dto.getId()));
            case WRITING -> ids.stream()
                    .map(testWritingService::findById)
                    .filter(TestWritingDTO::getStatus)
                    .anyMatch(dto -> testWritingService.verifyValidTestWriting(dto.getId()));
        };
    }



}
