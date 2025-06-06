package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.filter.SubmitTestFilterDTO;
import com.englishweb.h2t_backside.dto.test.*;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.SubmitTestMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.SubmitTest;
import com.englishweb.h2t_backside.model.test.SubmitTestWriting;
import com.englishweb.h2t_backside.repository.test.SubmitTestRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.SubmitTestAnswerService;
import com.englishweb.h2t_backside.service.test.SubmitTestService;
import com.englishweb.h2t_backside.service.test.SubmitTestSpeakingService;
import com.englishweb.h2t_backside.service.test.SubmitTestWritingService;
import com.englishweb.h2t_backside.utils.BaseFilterSpecification;
import com.englishweb.h2t_backside.utils.ParseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class SubmitTestServiceImpl extends BaseServiceImpl<SubmitTestDTO, SubmitTest, SubmitTestRepository> implements SubmitTestService {
    private final SubmitTestMapper mapper;
    private final SubmitTestAnswerService submitTestAnswerService;
    private final SubmitTestSpeakingService submitTestSpeakingService;
    private final SubmitTestWritingService submitTestWritingService;

    public SubmitTestServiceImpl(SubmitTestRepository repository, DiscordNotifier discordNotifier, SubmitTestMapper mapper, @Lazy SubmitTestAnswerService submitTestAnswerService, @Lazy SubmitTestSpeakingService submitTestSpeakingService, @Lazy SubmitTestWritingService submitTestWritingService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.submitTestAnswerService = submitTestAnswerService;
        this.submitTestSpeakingService = submitTestSpeakingService;
        this.submitTestWritingService = submitTestWritingService;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("SubmitTest with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(SubmitTestDTO dto, Exception ex) {
        log.error("Error creating submit test: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating submit test: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(SubmitTestDTO dto, Long id, Exception ex) {
        log.error("Error updating submit test: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating submit test: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("SubmitTest with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }


    @Override
    public boolean delete(Long id) {
        SubmitTestDTO dto = super.findById(id);

        List<SubmitTestSpeakingDTO> speakingList = submitTestSpeakingService.findBySubmitTestId(dto.getId());
        for (SubmitTestSpeakingDTO speaking : speakingList) {
            submitTestSpeakingService.delete(speaking.getId());
        }

        List<SubmitTestAnswerDTO> answerList = submitTestAnswerService.findBySubmitTestId(dto.getId());
        for (SubmitTestAnswerDTO answer : answerList) {
            submitTestAnswerService.delete(answer.getId());
        }

        List<SubmitTestWritingDTO> writingList = submitTestWritingService.findBySubmitTestId(dto.getId());
        for (SubmitTestWritingDTO writing : writingList) {
            submitTestWritingService.delete(writing.getId());
        }

        return super.delete(id);
    }


    @Override
    protected void patchEntityFromDTO(SubmitTestDTO dto, SubmitTest entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected SubmitTest convertToEntity(SubmitTestDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected SubmitTestDTO convertToDTO(SubmitTest entity) {
        return mapper.convertToDTO(entity);
    }
    @Override
    public double getScoreOfLastTestByUserIdAndTestId(Long userId, Long testId) {
        List<SubmitTest> submits = repository.findByUserIdAndTestIdAndStatusTrue(userId, testId);

        return submits.stream()
                .max(Comparator.comparing(SubmitTest::getCreatedAt))
                .map(SubmitTest::getScore)
                .map(Double::valueOf)
                .orElse(0.0);
    }

    @Override
    public int countSubmitByUserId(Long userId) {
        return repository.countByUserIdAndStatusTrue(userId);
    }

    @Override
    public double totalScoreByUserId(Long userId) {
        return repository.sumScoreByUserIdAndStatusTrue(userId);
    }
    @Override
    public SubmitTestDTO findByTestIdAndUserIdAndStatusFalse(Long testId, Long userId) {
        SubmitTest submitTest = repository.findByTestIdAndUserIdAndStatusFalse(testId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        testId,
                        String.format("SubmitTest with test ID '%d', userId '%d' and status=false not found.", testId, userId),
                        SeverityEnum.LOW
                ));
        return convertToDTO(submitTest);
    }


    @Override
    public List<SubmitTestDTO> findByTestId(Long testId) {
        return repository.findByTestId(testId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public Page<SubmitTestDTO> searchWithFilters(int page, int size, String sortFields, SubmitTestFilterDTO filter, Long userId) {
        Pageable pageable = ParseData.parsePageArgs(page, size, sortFields, SubmitTest.class);

        Specification<SubmitTest> specification = BaseFilterSpecification.applyBaseFilters(filter);

        if (userId != null) {
            specification = specification.and((root, query, cb) ->
                    cb.equal(root.get("user").get("id"), userId));
        }

        if (filter.getTitle() != null && !filter.getTitle().isEmpty()) {
            specification = specification.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("test").get("title")), "%" + filter.getTitle().toLowerCase() + "%"));
        }

        if (filter.getType() != null) {
            specification = specification.and((root, query, cb) ->
                    cb.equal(root.get("test").get("type"), filter.getType()));
        }

        return repository.findAll(specification, pageable)
                .map(mapper::convertToDTO);
    }

}
