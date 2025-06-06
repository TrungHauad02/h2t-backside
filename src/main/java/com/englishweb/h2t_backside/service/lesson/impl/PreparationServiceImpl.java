package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import com.englishweb.h2t_backside.dto.lesson.PreparationClassifyDTO;
import com.englishweb.h2t_backside.dto.lesson.PreparationDTO;
import com.englishweb.h2t_backside.dto.lesson.PreparationMakeSentencesDTO;
import com.englishweb.h2t_backside.dto.lesson.PreparationMatchWordSentencesDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.lesson.PreparationMapper;
import com.englishweb.h2t_backside.model.enummodel.PreparationEnum;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.lesson.Preparation;
import com.englishweb.h2t_backside.repository.lesson.PreparationRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.lesson.PreparationClassifyService;
import com.englishweb.h2t_backside.service.lesson.PreparationMakeSentencesService;
import com.englishweb.h2t_backside.service.lesson.PreparationMatchWordSentencesService;
import com.englishweb.h2t_backside.service.lesson.PreparationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PreparationServiceImpl extends BaseServiceImpl<PreparationDTO, Preparation, PreparationRepository>
        implements PreparationService {

    private final PreparationMapper mapper;
    private final PreparationClassifyService preparationClassifyService;
    private final PreparationMakeSentencesService preparationMakeSentencesService;
    private final PreparationMatchWordSentencesService preparationMatchWordSentencesService;

    public PreparationServiceImpl(PreparationRepository repository,
                                  DiscordNotifier discordNotifier,
                                  PreparationMapper mapper,
                                  @Lazy PreparationClassifyService preparationClassifyService,
                                  @Lazy PreparationMakeSentencesService preparationMakeSentencesService,
                                  @Lazy PreparationMatchWordSentencesService preparationMatchWordSentencesService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.preparationClassifyService = preparationClassifyService;
        this.preparationMakeSentencesService = preparationMakeSentencesService;
        this.preparationMatchWordSentencesService = preparationMatchWordSentencesService;
    }

    @Override
    public PreparationDTO update(PreparationDTO dto, Long id) {
        PreparationDTO exitingPreparation = super.findById(id);
        if (exitingPreparation.getType() != dto.getType()) {
            deleteQuestions(exitingPreparation, exitingPreparation.getType());
            dto.setQuestions(null);
        }
        return super.update(dto, id);
    }

    @Override
    public PreparationDTO patch(PreparationDTO dto, Long id) {
        if (dto.getType() == null) return super.patch(dto, id);

        PreparationDTO exitingPreparation = super.findById(id);
        if (exitingPreparation.getType() != dto.getType()) {
            deleteQuestions(exitingPreparation, exitingPreparation.getType());
            dto.setQuestions(null);
        }
        return super.patch(dto, id);
    }

    @Override
    public boolean delete(Long id) {
        // Delete other resources associated with the preparation
        PreparationDTO dto = super.findById(id);
        deleteQuestions(dto, dto.getType());
        return super.delete(id);
    }

    private void deleteQuestions(PreparationDTO dto, PreparationEnum type) {
        List<Long> questionIds = dto.getQuestions();

        if (!questionIds.isEmpty()) {
            switch (type) {
                case CLASSIFY -> preparationClassifyService.deleteAll(questionIds);
                case WORDS_MAKE_SENTENCES -> preparationMakeSentencesService.deleteAll(questionIds);
                default -> preparationMatchWordSentencesService.deleteAll(questionIds);
            }
        }
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("Preparation with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(PreparationDTO dto, Exception ex) {
        log.error("Error creating preparation: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating preparation: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.PREPARATION_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(PreparationDTO dto, Long id, Exception ex) {
        log.error("Error updating preparation: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating preparation: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.PREPARATION_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("Preparation with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(PreparationDTO dto, Preparation entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected Preparation convertToEntity(PreparationDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected PreparationDTO convertToDTO(Preparation entity) {
        return mapper.convertToDTO(entity);
    }

    @Override
    public boolean verifyValidPreparation(Long preparationId) {
        PreparationDTO dto = findById(preparationId);
        if (dto.getQuestions().isEmpty())
            return false;
        // Check if at least one question is valid and active
        switch (dto.getType()) {
            case CLASSIFY -> {
                List<PreparationClassifyDTO> preparationClassifies = preparationClassifyService.findByIds(dto.getQuestions());
                return preparationClassifies.stream().anyMatch(AbstractBaseDTO::getStatus);
            }
            case WORDS_MAKE_SENTENCES -> {
                List<PreparationMakeSentencesDTO> preparationMakeSentences = preparationMakeSentencesService.findByIds(dto.getQuestions());
                return preparationMakeSentences.stream().anyMatch(AbstractBaseDTO::getStatus);
            }
            default -> {
                List<PreparationMatchWordSentencesDTO> preparationMatchWordSentences = preparationMatchWordSentencesService.findByIds(dto.getQuestions());
                return preparationMatchWordSentences.stream().anyMatch(AbstractBaseDTO::getStatus);
            }
        }
    }
}
