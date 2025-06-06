package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.lesson.SpeakingConversationDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.lesson.SpeakingConversationMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.lesson.SpeakingConversation;
import com.englishweb.h2t_backside.repository.lesson.SpeakingConversationRepository;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.feature.impl.DiscordNotifierImpl;
import com.englishweb.h2t_backside.service.lesson.SpeakingConversationService;
import com.englishweb.h2t_backside.service.lesson.SpeakingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SpeakingConversationServiceImpl
        extends BaseServiceImpl<SpeakingConversationDTO, SpeakingConversation, SpeakingConversationRepository>
        implements SpeakingConversationService {

    private final SpeakingConversationMapper mapper;
    private final SpeakingService speakingService;

    public SpeakingConversationServiceImpl(SpeakingConversationRepository repository,
                                           DiscordNotifierImpl discordNotifier,
                                           SpeakingConversationMapper mapper, SpeakingService speakingService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.speakingService = speakingService;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("SpeakingConversation with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(SpeakingConversationDTO dto, Exception ex) {
        log.error("Error creating SpeakingConversation: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating SpeakingConversation: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(SpeakingConversationDTO dto, Long id, Exception ex) {
        log.error("Error updating SpeakingConversation: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating SpeakingConversation: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("SpeakingConversation with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(SpeakingConversationDTO dto, SpeakingConversation entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected SpeakingConversation convertToEntity(SpeakingConversationDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected SpeakingConversationDTO convertToDTO(SpeakingConversation entity) {
        return mapper.convertToDTO(entity);
    }

    @Override
    public List<SpeakingConversationDTO> findBySpeakingId(Long speakingId) {
        if(!speakingService.isExist(speakingId)) {
            throw new ResourceNotFoundException(speakingId, String.format("Speaking with ID '%d' not found.", speakingId), SeverityEnum.LOW);
        }
        return repository.findBySpeaking_Id(speakingId).stream()
                .map(this::convertToDTO)
                .toList();
    }
}
