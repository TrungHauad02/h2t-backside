package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.LessonQuestionDTO;
import com.englishweb.h2t_backside.dto.lesson.ListeningDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.ListeningService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/listenings")
@AllArgsConstructor
public class ListeningController {

    private final ListeningService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<ListeningDTO> findById(@PathVariable Long id) {
        ListeningDTO listening = service.findById(id);

        return ResponseDTO.<ListeningDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(listening)
                .message("Listening retrieved successfully")
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<ListeningDTO> create(@Valid @RequestBody ListeningDTO listeningDTO) {
        ListeningDTO createdListening = service.create(listeningDTO);

        return ResponseDTO.<ListeningDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdListening)
                .message("Listening created successfully")
                .build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<ListeningDTO> update(@PathVariable Long id, @Valid @RequestBody ListeningDTO listeningDTO) {
        ListeningDTO updatedListening = service.update(listeningDTO, id);

        return ResponseDTO.<ListeningDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedListening)
                .message("Listening updated successfully")
                .build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<ListeningDTO> patch(@PathVariable Long id, @RequestBody ListeningDTO listeningDTO) {
        ListeningDTO patchedListening = service.patch(listeningDTO, id);

        return ResponseDTO.<ListeningDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedListening)
                .message("Listening updated with patch successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        return ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Listening deleted successfully" : "Failed to delete listening")
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<Page<ListeningDTO>> searchWithFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String sortFields,
            @ModelAttribute LessonFilterDTO filter) {

        Page<ListeningDTO> listenings = service.searchWithFilters(
                page, size, sortFields, filter);

        return ResponseDTO.<Page<ListeningDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(listenings)
                .message("Listenings retrieved successfully with filters")
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
                .message("Questions retrieved successfully for the listening")
                .build();
    }

    @GetMapping("/{id}/verify")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> verify(@PathVariable Long id) {
        boolean result = service.verifyValidLesson(id);
        if (result) {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Listening lesson verified successfully")
                    .build();
        } else {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Listening lesson not valid")
                    .build();
        }
    }
}
