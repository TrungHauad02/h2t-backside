package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.lesson.LessonAnswerDTO;
import com.englishweb.h2t_backside.dto.lesson.LessonQuestionDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.lesson.LessonQuestionMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.lesson.LessonQuestion;
import com.englishweb.h2t_backside.repository.lesson.LessonQuestionRepository;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.feature.impl.DiscordNotifierImpl;
import com.englishweb.h2t_backside.service.lesson.LessonQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class LessonQuestionServiceImpl
        extends BaseServiceImpl<LessonQuestionDTO, LessonQuestion, LessonQuestionRepository>
        implements LessonQuestionService {

    private final LessonQuestionMapper mapper;

    public LessonQuestionServiceImpl(LessonQuestionRepository repository,
                                     DiscordNotifierImpl discordNotifier,
                                     LessonQuestionMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    public LessonQuestionDTO create(LessonQuestionDTO dto) {
        dto.setAnswers(dto.getAnswers().stream().peek(answer -> answer.setId(null)).toList());
        return super.create(dto);
    }

    @Override
    public LessonQuestionDTO update(LessonQuestionDTO dto, Long id) {
        dto.setAnswers(dto.getAnswers().stream().peek(answer ->{
            if (answer.getId() <= 0) {
                answer.setId(null);
            }
        }).toList());
        return super.update(dto, id);
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("LessonQuestion with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(LessonQuestionDTO dto, Exception ex) {
        log.error("Error creating LessonQuestion: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating LessonQuestion: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_QUESTION_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(LessonQuestionDTO dto, Long id, Exception ex) {
        log.error("Error updating LessonQuestion: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating LessonQuestion: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_QUESTION_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("LessonQuestion with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(LessonQuestionDTO dto, LessonQuestion entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected LessonQuestion convertToEntity(LessonQuestionDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected LessonQuestionDTO convertToDTO(LessonQuestion entity) {
        return mapper.convertToDTO(entity);
    }

    @Override
    public List<LessonQuestionDTO> findByIds(List<Long> ids) {
        List<LessonQuestionDTO> result = new LinkedList<>();
        for (Long id : ids) {
            result.add(findById(id));
        }
        return result;
    }

    @Override
    public boolean verifyValidQuestion(Long questionId) {
        LessonQuestionDTO question = this.findById(questionId);

        return !question.getAnswers().isEmpty() && // Check if there are answers
                question.getAnswers().stream()
                        .anyMatch(answer -> answer.isCorrect() && answer.getStatus()); // Check if there is at least one correct answer
    }
}
