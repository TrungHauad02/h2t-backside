package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.TestSpeakingDTO;
import com.englishweb.h2t_backside.dto.test.TestWritingDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.TestWritingMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.TestWriting;
import com.englishweb.h2t_backside.repository.test.TestWritingRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.TestWritingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class TestWritingServiceImpl extends BaseServiceImpl<TestWritingDTO, TestWriting, TestWritingRepository> implements TestWritingService {
    private final TestWritingMapper mapper;

    public TestWritingServiceImpl(TestWritingRepository repository, DiscordNotifier discordNotifier, TestWritingMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("TestWriting with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(TestWritingDTO dto, Exception ex) {
        log.error("Error creating test writing: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating test writing: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(TestWritingDTO dto, Long id, Exception ex) {
        log.error("Error updating test writing: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating test writing: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("TestWriting with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }
    @Override
    public List<TestWritingDTO> findByIdsAndStatus(List<Long> ids, Boolean status) {
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
    protected void patchEntityFromDTO(TestWritingDTO dto, TestWriting entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected TestWriting convertToEntity(TestWritingDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected TestWritingDTO convertToDTO(TestWriting entity) {
        return mapper.convertToDTO(entity);
    }
    @Override
    public List<TestWritingDTO> findByIds(List<Long> ids) {
        List<TestWritingDTO> result = new LinkedList<>();
        for (Long id : ids) {
            result.add(findById(id));
        }
        return result;
    }
    @Override
    public boolean verifyValidTestWriting(Long testWritingId) {
        TestWritingDTO dto = super.findById(testWritingId);

        return dto.getTopic() != null && !dto.getTopic().isEmpty()
                && dto.getMinWords() > 0
                && dto.getMaxWords() > dto.getMinWords() ;
    }


}
