package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.lesson.LessonAnswerDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.LessonAnswerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/lesson-answers")
@AllArgsConstructor
public class LessonAnswerController {

    private final LessonAnswerService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<LessonAnswerDTO> findById(@PathVariable Long id) {
        LessonAnswerDTO lessonAnswer = service.findById(id);

        return ResponseDTO.<LessonAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(lessonAnswer)
                .message("Lesson answer retrieved successfully")
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<LessonAnswerDTO> create(@Valid @RequestBody LessonAnswerDTO lessonAnswerDTO) {
        LessonAnswerDTO createdLessonAnswer = service.create(lessonAnswerDTO);

        return ResponseDTO.<LessonAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdLessonAnswer)
                .message("Lesson answer created successfully")
                .build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<LessonAnswerDTO> update(@PathVariable Long id, @Valid @RequestBody LessonAnswerDTO lessonAnswerDTO) {
        LessonAnswerDTO updatedLessonAnswer = service.update(lessonAnswerDTO, id);

        return ResponseDTO.<LessonAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedLessonAnswer)
                .message("Lesson answer updated successfully")
                .build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<LessonAnswerDTO> patch(@PathVariable Long id, @RequestBody LessonAnswerDTO lessonAnswerDTO) {
        LessonAnswerDTO patchedLessonAnswer = service.patch(lessonAnswerDTO, id);

        return ResponseDTO.<LessonAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedLessonAnswer)
                .message("Lesson answer updated with patch successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        return ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Lesson answer deleted successfully" : "Failed to delete lesson answer")
                .build();
    }
}
