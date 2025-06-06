package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.QuestionDTO;
import com.englishweb.h2t_backside.dto.test.TestWritingDTO;
import com.englishweb.h2t_backside.dto.test.ToeicPart1DTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.ToeicPart1Mapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.ToeicPart1;
import com.englishweb.h2t_backside.repository.test.ToeicPart1Repository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.ToeicPart1Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class ToeicPart1ServiceImpl extends BaseServiceImpl<ToeicPart1DTO, ToeicPart1, ToeicPart1Repository> implements ToeicPart1Service {
    private final ToeicPart1Mapper mapper;

    public ToeicPart1ServiceImpl(ToeicPart1Repository repository, DiscordNotifier discordNotifier, ToeicPart1Mapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("ToeicPart1 with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(ToeicPart1DTO dto, Exception ex) {
        log.error("Error creating ToeicPart1: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating ToeicPart1: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(ToeicPart1DTO dto, Long id, Exception ex) {
        log.error("Error updating ToeicPart1: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating ToeicPart1: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("ToeicPart1 with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(ToeicPart1DTO dto, ToeicPart1 entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected ToeicPart1 convertToEntity(ToeicPart1DTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected ToeicPart1DTO convertToDTO(ToeicPart1 entity) {
        return mapper.convertToDTO(entity);
    }
    @Override
    public List<ToeicPart1DTO> findByIds(List<Long> ids) {
        List<ToeicPart1DTO> result = new LinkedList<>();
        for (Long id : ids) {
            result.add(findById(id));
        }
        return result;
    }
    @Override
    public List<ToeicPart1DTO> findByIdsAndStatus(List<Long> ids, Boolean status) {
        if (status == null) {
            return repository.findAllById(ids)
                    .stream()
                    .map(this::convertToDTO).toList();
        }
        return repository.findByIdInAndStatus(ids, status)
                .stream()
                .map(this::convertToDTO).toList();
    }
}
