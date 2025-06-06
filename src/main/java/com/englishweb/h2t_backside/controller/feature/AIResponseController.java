package com.englishweb.h2t_backside.controller.feature;

import com.englishweb.h2t_backside.dto.ai.AIResponseDTO;
import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.filter.AIResponseFilterDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.ai.AIResponseService;
import com.englishweb.h2t_backside.service.feature.AuthenticateService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/ai-response")
@AllArgsConstructor
public class AIResponseController {
    private final AIResponseService service;
    private final AuthenticateService authenticateService;


    @GetMapping("/teacher-view")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<Page<AIResponseDTO>> searchForTeacherView(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String sortFields,
            @RequestParam(required = true) Long teacherId,
            @ModelAttribute AIResponseFilterDTO filter) {

        // Lấy data với điều kiện OR (status = false OR userId = teacherId)
        Page<AIResponseDTO> aiResponses = service.searchForTeacherView(
                page, size, sortFields, filter, teacherId);

        return ResponseDTO.<Page<AIResponseDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(aiResponses)
                .message("Teacher view AIResponses retrieved successfully")
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<Page<AIResponseDTO>> searchWithFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String sortFields,
            @ModelAttribute AIResponseFilterDTO filter) {

        Page<AIResponseDTO> aiResponse = service.searchWithFilters(
                page, size, sortFields, filter);

        return ResponseDTO.<Page<AIResponseDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(aiResponse)
                .message("AIResponses retrieved successfully with filters")
                .build();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<AIResponseDTO> findById(@PathVariable Long id) {
        AIResponseDTO aiResponseDTO = service.findById(id);
        return ResponseDTO.<AIResponseDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(aiResponseDTO)
                .message("AIResponse retrieved successfully")
                .build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<AIResponseDTO> update(@PathVariable Long id, @Valid @RequestBody AIResponseDTO aiResponseDTO) {
        AIResponseDTO updatedAIResponse = service.update(aiResponseDTO, id);
        return ResponseDTO.<AIResponseDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedAIResponse)
                .message("AIResponse updated successfully")
                .build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<AIResponseDTO> patch(@PathVariable Long id, @RequestBody AIResponseDTO aiResponseDTO) {
        AIResponseDTO patchedAIResponse = service.patch(aiResponseDTO, id);
        return ResponseDTO.<AIResponseDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedAIResponse)
                .message("AIResponse updated with patch successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> delete(@PathVariable Long id) {
        boolean result =  service.delete(id);
        return ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "AIResponse deleted successfully" : "Failed to delete AIResponse")
                .build();
    }
}