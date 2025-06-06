package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.filter.TestFilterDTO;
import com.englishweb.h2t_backside.dto.test.SubmitTestDTO;
import com.englishweb.h2t_backside.dto.test.TestDTO;
import com.englishweb.h2t_backside.dto.test.TestPartDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.TestMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.enummodel.TestPartEnum;
import com.englishweb.h2t_backside.model.enummodel.TestTypeEnum;
import com.englishweb.h2t_backside.model.test.Test;
import com.englishweb.h2t_backside.repository.specifications.TestSpecification;
import com.englishweb.h2t_backside.repository.test.TestRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.*;
import com.englishweb.h2t_backside.utils.BaseFilterSpecification;
import com.englishweb.h2t_backside.utils.ParseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TestServiceImpl extends BaseServiceImpl<TestDTO, Test, TestRepository> implements TestService {
    private final TestMapper mapper;
    private final SubmitTestService submitTestService;
    private final TestPartService testPartService;

    public TestServiceImpl(TestRepository repository, DiscordNotifier discordNotifier,
                           TestMapper mapper,
                           @Lazy SubmitTestService submitTestService,
                           @Lazy TestPartService testPartService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.submitTestService = submitTestService;
        this.testPartService = testPartService;
    }
    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("Test with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }


    @Override
    public TestDTO create(TestDTO dto) {

        List<Long> partIds = new ArrayList<>();
        if (dto.getType() == TestTypeEnum.MIXING) {
            for (TestPartEnum partType : List.of(
                    TestPartEnum.VOCABULARY,
                    TestPartEnum.GRAMMAR,
                    TestPartEnum.READING,
                    TestPartEnum.LISTENING,
                    TestPartEnum.SPEAKING,
                    TestPartEnum.WRITING
            )) {
                TestPartDTO part = new TestPartDTO();
                part.setType(partType);
                TestPartDTO savedPart = testPartService.create(part);
                partIds.add(savedPart.getId());
            }
        } else {
            TestPartDTO part = new TestPartDTO();
            part.setType(TestPartEnum.valueOf(dto.getType().name()));
            TestPartDTO savedPart = testPartService.create(part);
            partIds.add(savedPart.getId());
        }
        dto.setParts(partIds);
        return super.create(dto);
    }

    @Override
    protected void createError(TestDTO dto, Exception ex) {
        log.error("Error creating test: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating test: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(TestDTO dto, Long id, Exception ex) {
        log.error("Error updating test: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating test: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("Test with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    public boolean delete(Long id) {

        TestDTO dto = super.findById(id);
        List<SubmitTestDTO> submitTestDTOList = submitTestService.findByTestId(dto.getId());
        for (SubmitTestDTO submitTestDTO : submitTestDTOList) {
            testPartService.delete(submitTestDTO.getId());
        }

        List<Long> partIds = dto.getParts();
        for (Long partId : partIds) {
            testPartService.delete(partId);
        }

        return super.delete(id);
    }
    @Override
    protected void patchEntityFromDTO(TestDTO dto, Test entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected Test convertToEntity(TestDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected TestDTO convertToDTO(Test entity) {
        return mapper.convertToDTO(entity);
    }

    @Override
    public Page<TestDTO> searchWithFilters(int page, int size, String sortFields, TestFilterDTO filter, Long userId) {
        Pageable pageable = ParseData.parsePageArgs(page, size, sortFields, Test.class);

        Specification<Test> specification = BaseFilterSpecification.applyBaseFilters(filter);

        if (filter.getTitle() != null && !filter.getTitle().isEmpty()) {
            specification = specification.and(TestSpecification.findByName(filter.getTitle()));
        }

        if (filter.getType() != null) {
            specification = specification.and(TestSpecification.findByType(filter.getType()));
        }

        return repository.findAll(specification, pageable).map(entity -> {
            TestDTO dto = mapper.convertToDTO(entity);
            dto.setTotalQuestions(testPartService.countTotalQuestionsOfTest(dto.getParts(),true));
            dto.setScoreLastOfTest(submitTestService.getScoreOfLastTestByUserIdAndTestId(userId,dto.getId()));
            return dto;
        });
    }

    @Override
    public boolean verifyValidTest(Long testId) {
        TestDTO test = super.findById(testId);
        if (test.getParts() == null || test.getParts().isEmpty()) return false;

        List<TestPartDTO> parts = test.getParts().stream()
                .map(testPartService::findById)
                .toList();

        if (parts.isEmpty()) return false;

        if (test.getType() == TestTypeEnum.MIXING) {
            return parts.stream()
                    .allMatch(part -> part.getStatus() && testPartService.verifyValidTestPart(part.getId()));
        } else {

            return parts.stream()
                    .anyMatch(part -> part.getStatus() && testPartService.verifyValidTestPart(part.getId()));
        }
    }




}
