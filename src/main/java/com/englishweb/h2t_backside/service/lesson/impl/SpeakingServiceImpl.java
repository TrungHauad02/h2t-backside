package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.PreparationDTO;
import com.englishweb.h2t_backside.dto.lesson.SpeakingConversationDTO;
import com.englishweb.h2t_backside.dto.lesson.SpeakingDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.lesson.SpeakingMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.lesson.Speaking;
import com.englishweb.h2t_backside.repository.lesson.SpeakingRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.lesson.PreparationService;
import com.englishweb.h2t_backside.service.lesson.SpeakingConversationService;
import com.englishweb.h2t_backside.service.lesson.SpeakingService;
import com.englishweb.h2t_backside.utils.LessonPagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SpeakingServiceImpl extends BaseServiceImpl<SpeakingDTO, Speaking, SpeakingRepository> implements SpeakingService {

    private final SpeakingMapper mapper;
    private final PreparationService preparationService;
    private final SpeakingConversationService speakingConversationService;

    public SpeakingServiceImpl(SpeakingRepository repository, DiscordNotifier discordNotifier, @Lazy SpeakingMapper mapper,
                              @Lazy PreparationService preparationService,@Lazy SpeakingConversationService speakingConversationService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.preparationService = preparationService;
        this.speakingConversationService = speakingConversationService;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("Speaking with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(SpeakingDTO dto, Exception ex) {
        log.error("Error creating speaking: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating speaking: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(SpeakingDTO dto, Long id, Exception ex) {
        log.error("Error updating speaking: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating speaking: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("Speaking with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(SpeakingDTO dto, Speaking entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected Speaking convertToEntity(SpeakingDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected SpeakingDTO convertToDTO(Speaking entity) {
        return mapper.convertToDTO(entity);
    }

    @Override
    public Page<SpeakingDTO> searchWithFilters(int page, int size, String sortFields, LessonFilterDTO filter) {
        return LessonPagination.searchWithFiltersGeneric(
                page, size, sortFields, filter, repository, Speaking.class
        ).map(this::convertToDTO);
    }

    @Override
    public boolean verifyValidLesson(Long lessonId) {
        SpeakingDTO dto = super.findById(lessonId);
        if (dto.getPreparationId() == null)
            return false;
        // Check if the preparation is valid
        PreparationDTO preparation = preparationService.findById(dto.getPreparationId());
        if (!preparation.getStatus() ||
                !preparationService.verifyValidPreparation(dto.getPreparationId())) {
            return false;
        }
        // Check if at least one conversation is active
        List<SpeakingConversationDTO> speakingConversations = speakingConversationService.findBySpeakingId(lessonId);
        return speakingConversations.stream().anyMatch(SpeakingConversationDTO::getStatus);
    }
}