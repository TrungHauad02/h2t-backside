package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.lesson.PreparationMakeSentencesDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.lesson.PreparationMakeSentencesMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.lesson.PreparationMakeSentences;
import com.englishweb.h2t_backside.repository.lesson.PreparationMakeSentencesRepository;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.feature.impl.DiscordNotifierImpl;
import com.englishweb.h2t_backside.service.lesson.PreparationMakeSentencesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class PreparationMakeSentencesServiceImpl extends BaseServiceImpl<PreparationMakeSentencesDTO, PreparationMakeSentences, PreparationMakeSentencesRepository> implements PreparationMakeSentencesService {

    private final PreparationMakeSentencesMapper mapper;

    public PreparationMakeSentencesServiceImpl(PreparationMakeSentencesRepository repository, DiscordNotifierImpl discordNotifier, PreparationMakeSentencesMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("PreparationMakeSentences with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(PreparationMakeSentencesDTO dto, Exception ex) {
        log.error("Error creating PreparationMakeSentences: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating PreparationMakeSentences: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.PREPARATION_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(PreparationMakeSentencesDTO dto, Long id, Exception ex) {
        log.error("Error updating PreparationMakeSentences: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating PreparationMakeSentences: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.PREPARATION_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("PreparationMakeSentences with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(PreparationMakeSentencesDTO dto, PreparationMakeSentences entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected PreparationMakeSentences convertToEntity(PreparationMakeSentencesDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected PreparationMakeSentencesDTO convertToDTO(PreparationMakeSentences entity) {
        return mapper.convertToDTO(entity);
    }

    @Override
    public List<PreparationMakeSentencesDTO> findByIds(List<Long> ids) {
        List<PreparationMakeSentencesDTO> result = new LinkedList<>();
        for (Long id : ids) {
            result.add(findById(id));
        }
        return result;
    }
}