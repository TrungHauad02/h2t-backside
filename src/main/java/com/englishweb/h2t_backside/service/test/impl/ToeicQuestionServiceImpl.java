package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.ToeicPart3_4DTO;
import com.englishweb.h2t_backside.dto.test.ToeicQuestionDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.ToeicQuestionMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.ToeicQuestion;
import com.englishweb.h2t_backside.repository.test.ToeicQuestionRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.ToeicAnswerService;
import com.englishweb.h2t_backside.service.test.ToeicQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class ToeicQuestionServiceImpl extends BaseServiceImpl<ToeicQuestionDTO, ToeicQuestion, ToeicQuestionRepository>
        implements ToeicQuestionService {

    private final ToeicQuestionMapper mapper;
    private final ToeicAnswerService toeicAnswerService;

    public ToeicQuestionServiceImpl(ToeicQuestionRepository repository,
                                    DiscordNotifier discordNotifier,
                                    ToeicQuestionMapper mapper, ToeicAnswerService toeicAnswerService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.toeicAnswerService = toeicAnswerService;
    }

    @Override
    protected void findByIdError(Long id) {
        throw new ResourceNotFoundException(id, "ToeicQuestion not found", SeverityEnum.LOW);
    }

    @Override
    protected void createError(ToeicQuestionDTO dto, Exception ex) {
        throw new CreateResourceException(dto, "Error creating ToeicQuestion: " + ex.getMessage(),
                ErrorApiCodeContent.LESSON_CREATED_FAIL, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(ToeicQuestionDTO dto, Long id, Exception ex) {
        throw new UpdateResourceException(dto, "Error updating ToeicQuestion: " + ex.getMessage(),
                ErrorApiCodeContent.LESSON_UPDATED_FAIL, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }


    @Override
    public ToeicQuestionDTO create(ToeicQuestionDTO dto) {
        dto.setAnswers(dto.getAnswers().stream().peek(answer ->{
            if (answer.getId() <= 0) {
                answer.setId(null);
            }
        }).toList());
        return super.create(dto);
    }

    @Override
    public ToeicQuestionDTO update(ToeicQuestionDTO dto, Long id) {
        dto.setAnswers(dto.getAnswers().stream().peek(answer ->{
            if (answer.getId() <= 0) {
                answer.setId(null);
            }
        }).toList());
        return super.update(dto, id);
    }
    @Override
    public boolean delete(Long id) {
        if (!isExist(id)) {
            return false;
        }

        ToeicQuestion toeicQuestion = repository.findById(id).orElse(null);
        if (toeicQuestion != null && toeicQuestion.getAnswers() != null) {
            toeicQuestion.getAnswers().forEach(answer -> {
                if (answer.getId() != null) {
                    toeicAnswerService.delete(answer.getId()); // gọi service xóa answer
                }
            });
        }

        return super.delete(id);
    }


    @Override
    protected void patchEntityFromDTO(ToeicQuestionDTO dto, ToeicQuestion entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected ToeicQuestion convertToEntity(ToeicQuestionDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected ToeicQuestionDTO convertToDTO(ToeicQuestion entity) {
        return mapper.convertToDTO(entity);
    }

    @Override
    public List<ToeicQuestionDTO> findByIds(List<Long> ids) {
        List<ToeicQuestionDTO> result = new LinkedList<>();
        for (Long id : ids) {
            result.add(findById(id));
        }
        return result;
    }
    @Override
    public List<ToeicQuestionDTO> findByIdsAndStatus(List<Long> ids, Boolean status) {
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
