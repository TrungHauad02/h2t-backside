package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.SubmitCompetitionWritingDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.SubmitCompetitionWritingMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.SubmitCompetitionWriting;
import com.englishweb.h2t_backside.repository.test.SubmitCompetitionWritingRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.SubmitCompetitionWritingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SubmitCompetitionWritingServiceImpl extends BaseServiceImpl<SubmitCompetitionWritingDTO, SubmitCompetitionWriting, SubmitCompetitionWritingRepository> implements SubmitCompetitionWritingService {
    private final SubmitCompetitionWritingMapper mapper;

    public SubmitCompetitionWritingServiceImpl(SubmitCompetitionWritingRepository repository, DiscordNotifier discordNotifier, SubmitCompetitionWritingMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }


    @Override
    public List<SubmitCompetitionWritingDTO> findBySubmitCompetitionIdAndTestWritingId(Long submitCompetitionId, Long testWritingId) {
        return repository.findBySubmitCompetitionIdAndTestWritingId(submitCompetitionId, testWritingId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<SubmitCompetitionWritingDTO> findBySubmitCompetitionIdAndTestWritingIdIn(Long submitCompetitionId, List<Long> testWritingIds) {
        return repository.findBySubmitCompetitionIdAndTestWritingIdIn(submitCompetitionId, testWritingIds)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<SubmitCompetitionWritingDTO> findBySubmitCompetitionId(Long submitCompetitionId) {
        return repository.findBySubmitCompetitionId(submitCompetitionId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("SubmitCompetitionWriting with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(SubmitCompetitionWritingDTO dto, Exception ex) {
        log.error("Error creating submit competition writing: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating submit competition writing: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(SubmitCompetitionWritingDTO dto, Long id, Exception ex) {
        log.error("Error updating submit competition writing: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating submit competition writing: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("SubmitCompetitionWriting with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(SubmitCompetitionWritingDTO dto, SubmitCompetitionWriting entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected SubmitCompetitionWriting convertToEntity(SubmitCompetitionWritingDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected SubmitCompetitionWritingDTO convertToDTO(SubmitCompetitionWriting entity) {
        return mapper.convertToDTO(entity);
    }
}
