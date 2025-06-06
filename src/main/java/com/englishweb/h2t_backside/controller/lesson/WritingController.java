package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.WritingDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.WritingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/writings")
@AllArgsConstructor
public class WritingController {

    private final WritingService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<WritingDTO> findById(@PathVariable Long id) {
        WritingDTO writing = service.findById(id);

        return ResponseDTO.<WritingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(writing)
                .message("Writing retrieved successfully")
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<WritingDTO> create(@Valid @RequestBody WritingDTO writingDTO) {
        WritingDTO createdWriting = service.create(writingDTO);

        return ResponseDTO.<WritingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdWriting)
                .message("Writing created successfully")
                .build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<WritingDTO> update(@PathVariable Long id, @Valid @RequestBody WritingDTO writingDTO) {
        WritingDTO updatedWriting = service.update(writingDTO, id);

        return ResponseDTO.<WritingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedWriting)
                .message("Writing updated successfully")
                .build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<WritingDTO> patch(@PathVariable Long id, @RequestBody WritingDTO writingDTO) {
        WritingDTO patchedWriting = service.patch(writingDTO, id);

        return ResponseDTO.<WritingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedWriting)
                .message("Writing updated with patch successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        return ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Writing deleted successfully" : "Failed to delete writing")
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<Page<WritingDTO>> searchWithFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String sortFields,
            @ModelAttribute LessonFilterDTO filter) {

        Page<WritingDTO> writings = service.searchWithFilters(page, size, sortFields, filter);

        return ResponseDTO.<Page<WritingDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(writings)
                .message("Writings retrieved successfully with filters")
                .build();
    }

    @GetMapping("/{id}/verify")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> verify(@PathVariable Long id) {
        boolean result = service.verifyValidLesson(id);
        if (result) {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Writing lesson verified successfully")
                    .build();
        } else {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Writing lesson not valid")
                    .build();
        }
    }
}
