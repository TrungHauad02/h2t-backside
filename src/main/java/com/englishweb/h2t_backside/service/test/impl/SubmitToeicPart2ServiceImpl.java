package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.SubmitToeicPart2DTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.SubmitToeicPart2Mapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.SubmitToeicPart2;
import com.englishweb.h2t_backside.repository.test.SubmitToeicPart2Repository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.SubmitToeicPart2Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SubmitToeicPart2ServiceImpl extends BaseServiceImpl<SubmitToeicPart2DTO, SubmitToeicPart2, SubmitToeicPart2Repository> implements SubmitToeicPart2Service {
    private final SubmitToeicPart2Mapper mapper;

    public SubmitToeicPart2ServiceImpl(SubmitToeicPart2Repository repository, DiscordNotifier discordNotifier, SubmitToeicPart2Mapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("SubmitToeicPart2 with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(SubmitToeicPart2DTO dto, Exception ex) {
        log.error("Error creating submit toeic part 2: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating submit toeic part 2: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(SubmitToeicPart2DTO dto, Long id, Exception ex) {
        log.error("Error updating submit toeic part 2: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating submit toeic part 2: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("SubmitToeicPart2 with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }
    @Override
    public List<SubmitToeicPart2DTO> findBySubmitToeicIdAndToeicPart2Id(Long submitToeicId, Long testPart2Id) {
        return repository.findBySubmitToeicIdAndToeicPart2Id(submitToeicId, testPart2Id)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<SubmitToeicPart2DTO> findBySubmitToeicIdAndToeicPart2IdIn(Long submitToeicId, List<Long> testPart2Ids) {
        return repository.findBySubmitToeicIdAndToeicPart2IdIn(submitToeicId, testPart2Ids)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<SubmitToeicPart2DTO> findBySubmitToeicId(Long submitToeicId) {
        return repository.findBySubmitToeicId(submitToeicId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    protected void patchEntityFromDTO(SubmitToeicPart2DTO dto, SubmitToeicPart2 entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected SubmitToeicPart2 convertToEntity(SubmitToeicPart2DTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected SubmitToeicPart2DTO convertToDTO(SubmitToeicPart2 entity) {
        return mapper.convertToDTO(entity);
    }
}
