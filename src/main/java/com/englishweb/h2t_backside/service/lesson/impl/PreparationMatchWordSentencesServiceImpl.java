package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.lesson.PreparationMatchWordSentencesDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.lesson.PreparationMatchWordSentencesMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.lesson.PreparationMatchWordSentences;
import com.englishweb.h2t_backside.repository.lesson.PreparationMatchWordSentencesRepository;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.feature.impl.DiscordNotifierImpl;
import com.englishweb.h2t_backside.service.lesson.PreparationMatchWordSentencesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class PreparationMatchWordSentencesServiceImpl
        extends BaseServiceImpl<PreparationMatchWordSentencesDTO, PreparationMatchWordSentences, PreparationMatchWordSentencesRepository>
        implements PreparationMatchWordSentencesService {

    private final PreparationMatchWordSentencesMapper mapper;

    public PreparationMatchWordSentencesServiceImpl(PreparationMatchWordSentencesRepository repository,
                                                    DiscordNotifierImpl discordNotifier,
                                                    PreparationMatchWordSentencesMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("PreparationMatchWordSentences with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(PreparationMatchWordSentencesDTO dto, Exception ex) {
        log.error("Error creating PreparationMatchWordSentences: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating PreparationMatchWordSentences: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.PREPARATION_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(PreparationMatchWordSentencesDTO dto, Long id, Exception ex) {
        log.error("Error updating PreparationMatchWordSentences: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating PreparationMatchWordSentences: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.PREPARATION_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("PreparationMatchWordSentences with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(PreparationMatchWordSentencesDTO dto, PreparationMatchWordSentences entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected PreparationMatchWordSentences convertToEntity(PreparationMatchWordSentencesDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected PreparationMatchWordSentencesDTO convertToDTO(PreparationMatchWordSentences entity) {
        return mapper.convertToDTO(entity);
    }

    @Override
    public List<PreparationMatchWordSentencesDTO> findByIds(List<Long> ids) {
        List<PreparationMatchWordSentencesDTO> result = new LinkedList<>();
        for (Long id : ids) {
            result.add(findById(id));
        }
        return result;
    }
}
