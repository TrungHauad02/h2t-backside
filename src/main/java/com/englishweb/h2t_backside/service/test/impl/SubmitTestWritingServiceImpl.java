package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.SubmitTestAnswerDTO;
import com.englishweb.h2t_backside.dto.test.SubmitTestWritingDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.SubmitTestWritingMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.SubmitTestWriting;
import com.englishweb.h2t_backside.repository.test.SubmitTestWritingRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.SubmitTestWritingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SubmitTestWritingServiceImpl extends BaseServiceImpl<SubmitTestWritingDTO, SubmitTestWriting, SubmitTestWritingRepository> implements SubmitTestWritingService {
    private final SubmitTestWritingMapper mapper;

    public SubmitTestWritingServiceImpl(SubmitTestWritingRepository repository, DiscordNotifier discordNotifier, SubmitTestWritingMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }
    @Override
    public List<SubmitTestWritingDTO> findBySubmitTestIdAndTestWritingId(Long submitTestId, Long questionId) {
        return repository.findBySubmitTestIdAndTestWritingId(submitTestId, questionId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<SubmitTestWritingDTO> findBySubmitTestIdAndTestWriting_IdIn(Long submitTestId, List<Long> questionIds) {
        return repository.findBySubmitTestIdAndTestWriting_IdIn(submitTestId, questionIds)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }


    @Override
    public List<SubmitTestWritingDTO> findBySubmitTestId(Long submitTestId) {
        return repository.findBySubmitTestId(submitTestId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("SubmitTestWriting with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(SubmitTestWritingDTO dto, Exception ex) {
        log.error("Error creating submit test writing: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating submit test writing: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(SubmitTestWritingDTO dto, Long id, Exception ex) {
        log.error("Error updating submit test writing: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating submit test writing: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("SubmitTestWriting with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(SubmitTestWritingDTO dto, SubmitTestWriting entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected SubmitTestWriting convertToEntity(SubmitTestWritingDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected SubmitTestWritingDTO convertToDTO(SubmitTestWriting entity) {
        return mapper.convertToDTO(entity);
    }
}
