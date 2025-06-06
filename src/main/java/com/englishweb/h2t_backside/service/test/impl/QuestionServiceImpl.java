package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.lesson.LessonQuestionDTO;
import com.englishweb.h2t_backside.dto.test.AnswerDTO;
import com.englishweb.h2t_backside.dto.test.QuestionDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.QuestionMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.Question;
import com.englishweb.h2t_backside.repository.test.QuestionRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.AnswerService;
import com.englishweb.h2t_backside.service.test.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class QuestionServiceImpl extends BaseServiceImpl<QuestionDTO, Question, QuestionRepository> implements QuestionService {
    private final QuestionMapper mapper;
    private final AnswerService answerService;

    public QuestionServiceImpl(
            QuestionRepository repository,
            DiscordNotifier discordNotifier,
            QuestionMapper mapper,
            AnswerService answerService
    ) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.answerService = answerService;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("Question with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(QuestionDTO dto, Exception ex) {
        log.error("Error creating question: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating question: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(QuestionDTO dto, Long id, Exception ex) {
        log.error("Error updating question: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating question: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("Question with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }




    @Override
    public QuestionDTO create(QuestionDTO dto) {
        dto.setAnswers(dto.getAnswers().stream().peek(answer -> answer.setId(null)).toList());
        return super.create(dto);
    }


    @Override
    public QuestionDTO update(QuestionDTO dto, Long id) {
        dto.setAnswers(dto.getAnswers().stream().peek(answer ->{
            if (answer.getId() <= 0) {
                answer.setId(null);
            }
        }).toList());
        return super.update(dto, id);
    }


    @Override
    protected void patchEntityFromDTO(QuestionDTO dto, Question entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected Question convertToEntity(QuestionDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected QuestionDTO convertToDTO(Question entity) {
        return mapper.convertToDTO(entity);
    }
    @Override
    public List<QuestionDTO> findByIds(List<Long> ids) {
        List<QuestionDTO> result = new LinkedList<>();
        for (Long id : ids) {
            result.add(findById(id));
        }
        return result;
    }

    @Override
    public List<QuestionDTO> findByIdsAndStatus(List<Long> ids, Boolean status) {
        if (status == null) {
            return repository.findAllById(ids)
                    .stream()
                    .map(this::convertToDTO).toList();
        }
        return repository.findByIdInAndStatus(ids, status)
                .stream()
                .map(this::convertToDTO).toList();
    }

    @Override
    public boolean delete(Long id) {
        if (!isExist(id)) {
            return false;
        }

        Question question = repository.findById(id).orElse(null);
        if (question != null && question.getAnswers() != null) {
            question.getAnswers().forEach(answer -> {
                if (answer.getId() != null) {
                    answerService.delete(answer.getId());
                }
            });
        }

        return  super.delete(id);
    }
    @Override
    public boolean verifyValidQuestion(Long questionId) {
        QuestionDTO question = this.findById(questionId);

        return !question.getAnswers().isEmpty() &&
                question.getAnswers().stream()
                        .anyMatch(answer -> answer.getCorrect() && answer.getStatus());
    }


}
