package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.lesson.PreparationClassifyDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.lesson.PreparationClassifyMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.lesson.PreparationClassify;
import com.englishweb.h2t_backside.repository.lesson.PreparationClassifyRepository;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.feature.impl.DiscordNotifierImpl;
import com.englishweb.h2t_backside.service.lesson.PreparationClassifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class PreparationClassifyServiceImpl extends BaseServiceImpl<PreparationClassifyDTO, PreparationClassify, PreparationClassifyRepository> implements PreparationClassifyService {

    private final PreparationClassifyMapper mapper;

    public PreparationClassifyServiceImpl(PreparationClassifyRepository repository, DiscordNotifierImpl discordNotifier, PreparationClassifyMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("PreparationClassify with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(PreparationClassifyDTO dto, Exception ex) {
        log.error("Error creating PreparationClassify: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating PreparationClassify: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.PREPARATION_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(PreparationClassifyDTO dto, Long id, Exception ex) {
        log.error("Error updating PreparationClassify: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating PreparationClassify: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.PREPARATION_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("PreparationClassify with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(PreparationClassifyDTO dto, PreparationClassify entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected PreparationClassify convertToEntity(PreparationClassifyDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected PreparationClassifyDTO convertToDTO(PreparationClassify entity) {
        return mapper.convertToDTO(entity);
    }

    @Override
    public List<PreparationClassifyDTO> findByIds(List<Long> ids) {
        List<PreparationClassifyDTO> result = new LinkedList<>();
        for (Long id : ids) {
            result.add(findById(id));
        }
        return result;
    }
}