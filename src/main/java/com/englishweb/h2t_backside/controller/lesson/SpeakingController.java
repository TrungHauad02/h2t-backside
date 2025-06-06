package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.SpeakingDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.SpeakingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/speakings")
@AllArgsConstructor
public class SpeakingController {

    private final SpeakingService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<SpeakingDTO> findById(@PathVariable Long id) {
        SpeakingDTO speaking = service.findById(id);

        return ResponseDTO.<SpeakingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(speaking)
                .message("Speaking retrieved successfully")
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<SpeakingDTO> create(@Valid @RequestBody SpeakingDTO speakingDTO) {
        SpeakingDTO createdSpeaking = service.create(speakingDTO);

        return ResponseDTO.<SpeakingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdSpeaking)
                .message("Speaking created successfully")
                .build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<SpeakingDTO> update(@PathVariable Long id, @Valid @RequestBody SpeakingDTO speakingDTO) {
        SpeakingDTO updatedSpeaking = service.update(speakingDTO, id);

        return ResponseDTO.<SpeakingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedSpeaking)
                .message("Speaking updated successfully")
                .build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<SpeakingDTO> patch(@PathVariable Long id, @RequestBody SpeakingDTO speakingDTO) {
        SpeakingDTO patchedSpeaking = service.patch(speakingDTO, id);

        return ResponseDTO.<SpeakingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedSpeaking)
                .message("Speaking updated with patch successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        return ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Speaking deleted successfully" : "Failed to delete speaking")
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<Page<SpeakingDTO>> searchWithFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String sortFields,
            @ModelAttribute LessonFilterDTO filter) {

        Page<SpeakingDTO> speakings = service.searchWithFilters(
                page, size, sortFields, filter);

        return ResponseDTO.<Page<SpeakingDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(speakings)
                .message("Speakings retrieved successfully with filters")
                .build();
    }

    @GetMapping("/{id}/verify")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> verify(@PathVariable Long id) {
        boolean result = service.verifyValidLesson(id);
        if (result) {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Speaking lesson verified successfully")
                    .build();
        } else {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Speaking lesson not valid")
                    .build();
        }
    }
}
