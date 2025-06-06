package com.englishweb.h2t_backside.utils;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import com.englishweb.h2t_backside.dto.lesson.LessonQuestionDTO;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.service.lesson.LessonQuestionService;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class LessonQuestionFinder {

    /**
     * Finds questions by lesson ID with flexible error handling
     *
     * @param <T> Type of lesson DTO
     * @param lessonId ID of the lesson
     * @param status Optional filter by status (active/inactive)
     * @param resourceName Name of the resource (for error messages)
     * @param lessonQuestionService Service to fetch questions
     * @param dtoToQuestionIds Function to extract question IDs from a lesson DTO
     * @param findLessonById Function to find a lesson by ID
     * @return List of lesson questions
     * @throws ResourceNotFoundException if lesson or questions not found
     */
    public static <T> List<LessonQuestionDTO> findQuestionsByLessonId(
            Long lessonId,
            Boolean status,
            String resourceName,
            LessonQuestionService lessonQuestionService,
            Function<T, List<Long>> dtoToQuestionIds,
            Function<Long, T> findLessonById
    ) {
        try {
            // Get the lesson DTO
            T lessonDto = findLessonById.apply(lessonId);
            if (lessonDto == null) {
                throw new ResourceNotFoundException(lessonId,
                        String.format("%s with ID '%d' not found.", resourceName, lessonId),
                        SeverityEnum.LOW);
            }

            // Extract question IDs from the lesson DTO
            List<Long> questionIds = dtoToQuestionIds.apply(lessonDto);

            // If no questions, return empty list
            if (questionIds == null || questionIds.isEmpty()) {
                return Collections.emptyList();
            }

            // Get all questions
            List<LessonQuestionDTO> questions = lessonQuestionService.findByIds(questionIds);

            // Apply status filter if requested
            if (status != null && status) {
                return questions.stream()
                        .filter(question -> question.getStatus() &&
                                lessonQuestionService.verifyValidQuestion(question.getId()))
                        .peek(question -> // Filter answers by status
                                question.setAnswers(
                                        question.getAnswers().stream()
                                                .filter(AbstractBaseDTO::getStatus)
                                                .toList()
                                )
                        )
                        .toList();
            }

            return questions;

        } catch (ResourceNotFoundException ex) {
            String errorMessage = String.format("Error finding questions for %s with ID '%d': %s",
                    resourceName.toLowerCase(), lessonId, ex.getMessage());
            throw new ResourceNotFoundException(ex.getResourceId(), errorMessage, SeverityEnum.LOW);
        } catch (Exception ex) {
            String errorMessage = String.format("Unexpected error finding questions for %s with ID '%d': %s",
                    resourceName.toLowerCase(), lessonId, ex.getMessage());
            throw new ResourceNotFoundException(lessonId, errorMessage, SeverityEnum.LOW);
        }
    }
}