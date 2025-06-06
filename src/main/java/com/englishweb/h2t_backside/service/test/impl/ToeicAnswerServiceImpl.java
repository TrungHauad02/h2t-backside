package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.AnswerDTO;
import com.englishweb.h2t_backside.dto.test.ToeicAnswerDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.ToeicAnswerMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.Answer;
import com.englishweb.h2t_backside.model.test.ToeicAnswer;
import com.englishweb.h2t_backside.repository.test.ToeicAnswerRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.ToeicAnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ToeicAnswerServiceImpl extends BaseServiceImpl<ToeicAnswerDTO, ToeicAnswer, ToeicAnswerRepository>
        implements ToeicAnswerService {

    private final ToeicAnswerMapper mapper;

    public ToeicAnswerServiceImpl(ToeicAnswerRepository repository, DiscordNotifier discordNotifier, ToeicAnswerMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        log.warn("ToeicAnswer with ID '{}' not found.", id);
        throw new ResourceNotFoundException(id, "ToeicAnswer not found", SeverityEnum.LOW);
    }

    @Override
    protected void createError(ToeicAnswerDTO dto, Exception ex) {
        log.error("Error creating ToeicAnswer: {}", ex.getMessage());
        throw new CreateResourceException(dto, "Failed to create ToeicAnswer: " + ex.getMessage(),
                ErrorApiCodeContent.LESSON_CREATED_FAIL, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(ToeicAnswerDTO dto, Long id, Exception ex) {
        log.error("Error updating ToeicAnswer: {}", ex.getMessage());
        HttpStatus status = this.isExist(id) ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.NOT_FOUND;
        throw new UpdateResourceException(dto, "Failed to update ToeicAnswer: " + ex.getMessage(),
                ErrorApiCodeContent.LESSON_UPDATED_FAIL, status, SeverityEnum.MEDIUM);
    }
    public List<ToeicAnswerDTO> findByQuestionId(Long questionId) {
        List<ToeicAnswer> answers = repository.findByQuestionId(questionId);
        return answers.stream().map(mapper::convertToDTO).toList();
    }

    @Override
    protected void patchEntityFromDTO(ToeicAnswerDTO dto, ToeicAnswer entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected ToeicAnswer convertToEntity(ToeicAnswerDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected ToeicAnswerDTO convertToDTO(ToeicAnswer entity) {
        return mapper.convertToDTO(entity);
    }
}
