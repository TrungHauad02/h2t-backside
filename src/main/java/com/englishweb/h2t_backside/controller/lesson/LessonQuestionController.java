package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.lesson.LessonQuestionDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.LessonQuestionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/lesson-questions")
@AllArgsConstructor
public class LessonQuestionController {

    private final LessonQuestionService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<LessonQuestionDTO> findById(@PathVariable Long id) {
        LessonQuestionDTO lessonQuestion = service.findById(id);

        return ResponseDTO.<LessonQuestionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(lessonQuestion)
                .message("Lesson question retrieved successfully")
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<LessonQuestionDTO> create(@Valid @RequestBody LessonQuestionDTO lessonQuestionDTO) {
        LessonQuestionDTO createdLessonQuestion = service.create(lessonQuestionDTO);

        return ResponseDTO.<LessonQuestionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdLessonQuestion)
                .message("Lesson question created successfully")
                .build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<LessonQuestionDTO> update(@PathVariable Long id, @Valid @RequestBody LessonQuestionDTO lessonQuestionDTO) {
        LessonQuestionDTO updatedLessonQuestion = service.update(lessonQuestionDTO, id);

        return ResponseDTO.<LessonQuestionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedLessonQuestion)
                .message("Lesson question updated successfully")
                .build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<LessonQuestionDTO> patch(@PathVariable Long id, @RequestBody LessonQuestionDTO lessonQuestionDTO) {
        LessonQuestionDTO patchedLessonQuestion = service.patch(lessonQuestionDTO, id);

        return ResponseDTO.<LessonQuestionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedLessonQuestion)
                .message("Lesson question updated with patch successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        return ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Lesson question deleted successfully" : "Failed to delete lesson question")
                .build();
    }

    @GetMapping("/{id}/verify")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> verify(@PathVariable Long id) {
        boolean result = service.verifyValidQuestion(id);
        if (result) {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Lesson question verified successfully")
                    .build();
        } else {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Lesson question not valid")
                    .build();
        }
    }
}
