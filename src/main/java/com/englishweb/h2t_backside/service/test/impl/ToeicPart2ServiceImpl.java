package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.ToeicPart1DTO;
import com.englishweb.h2t_backside.dto.test.ToeicPart2DTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.ToeicPart2Mapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.ToeicPart2;
import com.englishweb.h2t_backside.repository.test.ToeicPart2Repository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.ToeicPart2Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class ToeicPart2ServiceImpl extends BaseServiceImpl<ToeicPart2DTO, ToeicPart2, ToeicPart2Repository> implements ToeicPart2Service {
    private final ToeicPart2Mapper mapper;

    public ToeicPart2ServiceImpl(ToeicPart2Repository repository, DiscordNotifier discordNotifier, ToeicPart2Mapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("ToeicPart2 with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(ToeicPart2DTO dto, Exception ex) {
        log.error("Error creating ToeicPart2: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating ToeicPart2: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(ToeicPart2DTO dto, Long id, Exception ex) {
        log.error("Error updating ToeicPart2: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating ToeicPart2: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("ToeicPart2 with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(ToeicPart2DTO dto, ToeicPart2 entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected ToeicPart2 convertToEntity(ToeicPart2DTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected ToeicPart2DTO convertToDTO(ToeicPart2 entity) {
        return mapper.convertToDTO(entity);
    }
    @Override
    public List<ToeicPart2DTO> findByIds(List<Long> ids) {
        List<ToeicPart2DTO> result = new LinkedList<>();
        for (Long id : ids) {
            result.add(findById(id));
        }
        return result;
    }
    @Override
    public List<ToeicPart2DTO> findByIdsAndStatus(List<Long> ids, Boolean status) {
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
