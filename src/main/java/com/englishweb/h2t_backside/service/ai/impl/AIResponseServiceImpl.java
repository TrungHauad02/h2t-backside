package com.englishweb.h2t_backside.service.ai.impl;

import com.englishweb.h2t_backside.dto.ai.AIResponseDTO;
import com.englishweb.h2t_backside.dto.feature.UserDTO;
import com.englishweb.h2t_backside.dto.filter.AIResponseFilterDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.AIResponseMapper;
import com.englishweb.h2t_backside.model.enummodel.RoleEnum;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.features.AIResponse;
import com.englishweb.h2t_backside.repository.AIResponseRepository;
import com.englishweb.h2t_backside.service.ai.AIResponseService;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.utils.AIResponseOrPagination;
import com.englishweb.h2t_backside.utils.AIResponsePagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AIResponseServiceImpl extends BaseServiceImpl<AIResponseDTO, AIResponse, AIResponseRepository> implements AIResponseService {
    private final AIResponseMapper mapper;
    private static final String RESOURCE_NAME = "AIResponse";

    public AIResponseServiceImpl(AIResponseRepository repository, DiscordNotifier discordNotifier, AIResponseMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("%s with ID '%d' not found.", RESOURCE_NAME, id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(AIResponseDTO dto, Exception ex) {
        log.error("Error creating {}: {}", RESOURCE_NAME.toLowerCase(), ex.getMessage());
        String errorMessage = String.format("Unexpected error creating %s: %s", RESOURCE_NAME.toLowerCase(), ex.getMessage());
        String errorCode = ErrorApiCodeContent.AIRESPONSE_CREATED_FAIL;

        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(AIResponseDTO dto, Long id, Exception ex) {
        log.error("Error updating {}: {}", RESOURCE_NAME.toLowerCase(), ex.getMessage());
        String errorMessage = String.format("Unexpected error updating %s: %s", RESOURCE_NAME.toLowerCase(), ex.getMessage());
        String errorCode = ErrorApiCodeContent.AIRESPONSE_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)){
            errorMessage = String.format("%s with ID '%d' not found.", RESOURCE_NAME, id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    public AIResponseDTO create(AIResponseDTO aiResponseDTO) {
        aiResponseDTO.setStatus(false);
        return super.create(aiResponseDTO);
    }

    @Override
    protected void patchEntityFromDTO(AIResponseDTO dto, AIResponse entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected AIResponse convertToEntity(AIResponseDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected AIResponseDTO convertToDTO(AIResponse entity) {
        return mapper.convertToDTO(entity);
    }

    @Override
    public Page<AIResponseDTO> searchWithFilters(int page, int size, String sortFields, AIResponseFilterDTO filter) {
        return AIResponsePagination.searchWithFiltersGeneric(
                page, size, sortFields, filter, repository, AIResponse.class
        ).map(this::convertToDTO);
    }

    @Override
    public Page<AIResponseDTO> searchForTeacherView(int page, int size, String sortFields,
                                                    AIResponseFilterDTO filter, Long teacherId) {
        return AIResponseOrPagination.searchWithOrCondition(
                page, size, sortFields, filter, repository, AIResponse.class, teacherId
        ).map(this::convertToDTO);
    }

}
