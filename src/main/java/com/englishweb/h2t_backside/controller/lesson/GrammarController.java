package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.GrammarDTO;
import com.englishweb.h2t_backside.dto.lesson.LessonQuestionDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.GrammarService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/grammars")
@AllArgsConstructor
public class GrammarController {

    private final GrammarService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<GrammarDTO> findById(@PathVariable Long id) {
        GrammarDTO grammar = service.findById(id);

        return ResponseDTO.<GrammarDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(grammar)
                .message("Grammar retrieved successfully")
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<GrammarDTO> create(@Valid @RequestBody GrammarDTO grammarDTO) {
        GrammarDTO createdGrammar = service.create(grammarDTO);

        return ResponseDTO.<GrammarDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdGrammar)
                .message("Grammar created successfully")
                .build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<GrammarDTO> update(@PathVariable Long id, @Valid @RequestBody GrammarDTO grammarDTO) {
        GrammarDTO updatedGrammar = service.update(grammarDTO, id);

        return ResponseDTO.<GrammarDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedGrammar)
                .message("Grammar updated successfully")
                .build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<GrammarDTO> patch(@PathVariable Long id, @RequestBody GrammarDTO grammarDTO) {
        GrammarDTO patchedGrammar = service.patch(grammarDTO, id);

        return ResponseDTO.<GrammarDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedGrammar)
                .message("Grammar updated with patch successfully")
                .build();
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> delete(@PathVariable Long id) {
        boolean result =  service.delete(id);

        return ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Grammar deleted successfully" : "Failed to delete grammar")
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<Page<GrammarDTO>> searchWithFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String sortFields,
            @ModelAttribute LessonFilterDTO filter) {

        Page<GrammarDTO> grammars = service.searchWithFilters(
                page, size, sortFields, filter);

        return ResponseDTO.<Page<GrammarDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(grammars)
                .message("Grammars retrieved successfully with filters")
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
                .message("Questions retrieved successfully")
                .build();
    }

    @GetMapping("/{id}/verify")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> verify(@PathVariable Long id) {
        boolean result = service.verifyValidLesson(id);
        if (result) {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Grammar lesson verified successfully")
                    .build();
        } else {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Grammar lesson not valid")
                    .build();
        }
    }
}
