package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.LessonQuestionDTO;
import com.englishweb.h2t_backside.dto.lesson.ReadingDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.ReadingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/readings")
@AllArgsConstructor
public class ReadingController {

    private final ReadingService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<ReadingDTO> findById(@PathVariable Long id) {
        ReadingDTO reading = service.findById(id);

        return ResponseDTO.<ReadingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(reading)
                .message("Reading retrieved successfully")
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<ReadingDTO> create(@Valid @RequestBody ReadingDTO readingDTO) {
        ReadingDTO createdReading = service.create(readingDTO);

        return ResponseDTO.<ReadingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdReading)
                .message("Reading created successfully")
                .build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<ReadingDTO> update(@PathVariable Long id, @Valid @RequestBody ReadingDTO readingDTO) {
        ReadingDTO updatedReading = service.update(readingDTO, id);

        return ResponseDTO.<ReadingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedReading)
                .message("Reading updated successfully")
                .build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<ReadingDTO> patch(@PathVariable Long id, @RequestBody ReadingDTO readingDTO) {
        ReadingDTO patchedReading = service.patch(readingDTO, id);

        return ResponseDTO.<ReadingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedReading)
                .message("Reading updated with patch successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        return ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Reading deleted successfully" : "Failed to delete reading")
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<Page<ReadingDTO>> searchWithFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String sortFields,
            @ModelAttribute LessonFilterDTO filter) {

        Page<ReadingDTO> readings = service.searchWithFilters(page, size, sortFields, filter);

        return ResponseDTO.<Page<ReadingDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(readings)
                .message("Readings retrieved successfully with filters")
                .build();
    }

    @GetMapping("/questions")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<LessonQuestionDTO>> findQuestionByTopicId(
            @RequestParam Long lessonId,
            @RequestParam (required = false) Boolean status) {
        List<LessonQuestionDTO> questions = service.findQuestionByLessonId(lessonId, status);

        return ResponseDTO.<List<LessonQuestionDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(questions)
                .message("Questions retrieved successfully for the reading")
                .build();
    }

    @GetMapping("/{id}/verify")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> verify(@PathVariable Long id) {
        boolean result = service.verifyValidLesson(id);
        if (result) {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Reading lesson verified successfully")
                    .build();
        } else {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Reading lesson not valid")
                    .build();
        }
    }
}
