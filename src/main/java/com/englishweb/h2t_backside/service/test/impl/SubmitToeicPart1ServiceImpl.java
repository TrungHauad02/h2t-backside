package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.SubmitToeicPart1DTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.SubmitToeicPart1Mapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.SubmitToeicPart1;
import com.englishweb.h2t_backside.repository.test.SubmitToeicPart1Repository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.SubmitToeicPart1Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SubmitToeicPart1ServiceImpl extends BaseServiceImpl<SubmitToeicPart1DTO, SubmitToeicPart1, SubmitToeicPart1Repository> implements SubmitToeicPart1Service {
    private final SubmitToeicPart1Mapper mapper;

    public SubmitToeicPart1ServiceImpl(SubmitToeicPart1Repository repository, DiscordNotifier discordNotifier, SubmitToeicPart1Mapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("SubmitToeicPart1 with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(SubmitToeicPart1DTO dto, Exception ex) {
        log.error("Error creating submit toeic part 1: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating submit toeic part 1: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(SubmitToeicPart1DTO dto, Long id, Exception ex) {
        log.error("Error updating submit toeic part 1: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating submit toeic part 1: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("SubmitToeicPart1 with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    public List<SubmitToeicPart1DTO> findBySubmitToeicIdAndToeicPart1Id(Long submitToeicId, Long testPart1Id) {
        return repository.findBySubmitToeicIdAndToeicPart1Id(submitToeicId, testPart1Id)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<SubmitToeicPart1DTO> findBySubmitToeicIdAndToeicPart1IdIn(Long submitToeicId, List<Long> toeicPart1Ids) {
        return repository.findBySubmitToeicIdAndToeicPart1IdIn(submitToeicId, toeicPart1Ids)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<SubmitToeicPart1DTO> findBySubmitToeicId(Long submitToeicId) {
        return repository.findBySubmitToeicId(submitToeicId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    protected void patchEntityFromDTO(SubmitToeicPart1DTO dto, SubmitToeicPart1 entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected SubmitToeicPart1 convertToEntity(SubmitToeicPart1DTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected SubmitToeicPart1DTO convertToDTO(SubmitToeicPart1 entity) {
        return mapper.convertToDTO(entity);
    }
}
