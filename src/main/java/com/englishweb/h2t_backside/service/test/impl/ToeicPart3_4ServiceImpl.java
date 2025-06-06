package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.ToeicPart2DTO;
import com.englishweb.h2t_backside.dto.test.ToeicPart3_4DTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.ToeicPart3_4Mapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.ToeicPart3_4;
import com.englishweb.h2t_backside.repository.test.ToeicPart3_4Repository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.ToeicPart3_4Service;
import com.englishweb.h2t_backside.service.test.ToeicQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class ToeicPart3_4ServiceImpl extends BaseServiceImpl<ToeicPart3_4DTO, ToeicPart3_4, ToeicPart3_4Repository> implements ToeicPart3_4Service {
    private final ToeicPart3_4Mapper mapper;
    private final ToeicQuestionService toeicQuestionService;

    public ToeicPart3_4ServiceImpl(ToeicPart3_4Repository repository, DiscordNotifier discordNotifier, ToeicPart3_4Mapper mapper,@Lazy ToeicQuestionService toeicQuestionService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.toeicQuestionService = toeicQuestionService;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("ToeicPart3_4 with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(ToeicPart3_4DTO dto, Exception ex) {
        log.error("Error creating ToeicPart3_4: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating ToeicPart3_4: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(ToeicPart3_4DTO dto, Long id, Exception ex) {
        log.error("Error updating ToeicPart3_4: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating ToeicPart3_4: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("ToeicPart3_4 with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }
    @Override
    public boolean delete(Long id) {
        ToeicPart3_4DTO dto = super.findById(id);
        if (dto == null) {
            return false;
        }

        if (dto.getQuestions() != null && !dto.getQuestions().isEmpty()) {
            for (Long questionId : dto.getQuestions()) {
                toeicQuestionService.delete(questionId);
            }
        }
        return super.delete(id);
    }


    @Override
    protected void patchEntityFromDTO(ToeicPart3_4DTO dto, ToeicPart3_4 entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected ToeicPart3_4 convertToEntity(ToeicPart3_4DTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected ToeicPart3_4DTO convertToDTO(ToeicPart3_4 entity) {
        return mapper.convertToDTO(entity);
    }
    @Override
    public List<ToeicPart3_4DTO> findByIds(List<Long> ids) {
        List<ToeicPart3_4DTO> result = new LinkedList<>();
        for (Long id : ids) {
            result.add(findById(id));
        }
        return result;
    }
    @Override
    public List<ToeicPart3_4DTO> findByIdsAndStatus(List<Long> ids, Boolean status) {
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
