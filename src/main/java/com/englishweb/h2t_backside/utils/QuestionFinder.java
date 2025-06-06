package com.englishweb.h2t_backside.utils;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import com.englishweb.h2t_backside.dto.test.QuestionDTO;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.service.test.QuestionService;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class QuestionFinder {

    /**
     * Finds questions by test part ID with flexible error handling
     *
     * @param <T> Type of test part DTO
     * @param partId ID of the test part
     * @param status Optional filter by status (active/inactive)
     * @param resourceName Name of the resource (for error messages)
     * @param QuestionService Service to fetch test questions
     * @param dtoToQuestionIds Function to extract question IDs from a test part DTO
     * @param findPartById Function to find a test part by ID
     * @return List of test questions
     * @throws ResourceNotFoundException if part or questions not found
     */
    public static <T> List<QuestionDTO> findQuestionsByTestId(
            Long partId,
            Boolean status,
            String resourceName,
            QuestionService QuestionService,
            Function<T, List<Long>> dtoToQuestionIds,
            Function<Long, T> findPartById
    ) {
        try {
            // Get the test part DTO
            T partDto = findPartById.apply(partId);
            if (partDto == null) {
                throw new ResourceNotFoundException(partId,
                        String.format("%s with ID '%d' not found.", resourceName, partId),
                        SeverityEnum.LOW);
            }

            // Extract question IDs from the part DTO
            List<Long> questionIds = dtoToQuestionIds.apply(partDto);

            // If no questions, return empty list
            if (questionIds == null || questionIds.isEmpty()) {
                return Collections.emptyList();
            }

            // Get all test questions
            List<QuestionDTO> questions = QuestionService.findByIds(questionIds);

            // Apply status filter if requested
            if (status != null && status) {
                return questions.stream()
                        .filter(question -> question.getStatus() &&
                                QuestionService.verifyValidQuestion(question.getId()))
                        .peek(question ->
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
                    resourceName.toLowerCase(), partId, ex.getMessage());
            throw new ResourceNotFoundException(ex.getResourceId(), errorMessage, SeverityEnum.LOW);
        } catch (Exception ex) {
            String errorMessage = String.format("Unexpected error finding questions for %s with ID '%d': %s",
                    resourceName.toLowerCase(), partId, ex.getMessage());
            throw new ResourceNotFoundException(partId, errorMessage, SeverityEnum.LOW);
        }
    }
}
