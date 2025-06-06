package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.ToeicPart3_4DTO;
import com.englishweb.h2t_backside.dto.test.ToeicPart6DTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.ToeicPart6Mapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.ToeicPart6;
import com.englishweb.h2t_backside.repository.test.ToeicPart6Repository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.ToeicPart6Service;
import com.englishweb.h2t_backside.service.test.ToeicQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class ToeicPart6ServiceImpl extends BaseServiceImpl<ToeicPart6DTO, ToeicPart6, ToeicPart6Repository> implements ToeicPart6Service {
    private final ToeicPart6Mapper mapper;
    private final ToeicQuestionService toeicQuestionService;

    public ToeicPart6ServiceImpl(ToeicPart6Repository repository, DiscordNotifier discordNotifier, ToeicPart6Mapper mapper,@Lazy ToeicQuestionService toeicQuestionService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.toeicQuestionService = toeicQuestionService;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("ToeicPart6 with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(ToeicPart6DTO dto, Exception ex) {
        log.error("Error creating ToeicPart6: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating ToeicPart6: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(ToeicPart6DTO dto, Long id, Exception ex) {
        log.error("Error updating ToeicPart6: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating ToeicPart6: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("ToeicPart6 with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }
    @Override
    public boolean delete(Long id) {
        ToeicPart6DTO dto = super.findById(id);
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
    protected void patchEntityFromDTO(ToeicPart6DTO dto, ToeicPart6 entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected ToeicPart6 convertToEntity(ToeicPart6DTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected ToeicPart6DTO convertToDTO(ToeicPart6 entity) {
        return mapper.convertToDTO(entity);
    }
    @Override
    public List<ToeicPart6DTO> findByIds(List<Long> ids) {
        List<ToeicPart6DTO> result = new LinkedList<>();
        for (Long id : ids) {
            result.add(findById(id));
        }
        return result;
    }
    @Override
    public List<ToeicPart6DTO> findByIdsAndStatus(List<Long> ids, Boolean status) {
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
