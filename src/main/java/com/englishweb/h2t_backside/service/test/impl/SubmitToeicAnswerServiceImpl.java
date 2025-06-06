package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.SubmitToeicAnswerDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.SubmitToeicAnswerMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.SubmitToeicAnswer;
import com.englishweb.h2t_backside.repository.test.SubmitToeicAnswerRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.SubmitToeicAnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SubmitToeicAnswerServiceImpl extends BaseServiceImpl<SubmitToeicAnswerDTO, SubmitToeicAnswer, SubmitToeicAnswerRepository>
        implements SubmitToeicAnswerService {

    private final SubmitToeicAnswerMapper mapper;

    public SubmitToeicAnswerServiceImpl(SubmitToeicAnswerRepository repository,
                                        DiscordNotifier discordNotifier,
                                        SubmitToeicAnswerMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }
    @Override
    public List<SubmitToeicAnswerDTO> findBySubmitToeicIdAndQuestionId(Long submitToeicId, Long questionId) {
        return repository.findBySubmitToeicIdAndQuestionId(submitToeicId, questionId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<SubmitToeicAnswerDTO> findBySubmitToeicIdAndQuestionIdIn(Long submitToeicId, List<Long> questionIds) {
        return repository.findBySubmitToeicIdAndQuestionIdIn(submitToeicId, questionIds)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }
    @Override
    public List<SubmitToeicAnswerDTO>  findBySubmitToeicId(Long submitToeicId) {
        return repository.findBySubmitToeicId(submitToeicId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("SubmitToeicAnswer with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(SubmitToeicAnswerDTO dto, Exception ex) {
        log.error("Error creating SubmitToeicAnswer: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating SubmitToeicAnswer: " + ex.getMessage();
        throw new CreateResourceException(dto, errorMessage, ErrorApiCodeContent.LESSON_CREATED_FAIL,
                HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(SubmitToeicAnswerDTO dto, Long id, Exception ex) {
        log.error("Error updating SubmitToeicAnswer: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating SubmitToeicAnswer: " + ex.getMessage();
        HttpStatus status = this.isExist(id) ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.NOT_FOUND;
        throw new UpdateResourceException(dto, errorMessage, ErrorApiCodeContent.LESSON_UPDATED_FAIL,
                status, SeverityEnum.HIGH);
    }

    @Override
    protected void patchEntityFromDTO(SubmitToeicAnswerDTO dto, SubmitToeicAnswer entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected SubmitToeicAnswer convertToEntity(SubmitToeicAnswerDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected SubmitToeicAnswerDTO convertToDTO(SubmitToeicAnswer entity) {
        return mapper.convertToDTO(entity);
    }
}
