package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.filter.SubmitToeicFilterDTO;
import com.englishweb.h2t_backside.dto.test.SubmitToeicDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.SubmitToeicService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/submit-toeic")
@AllArgsConstructor
public class SubmitToeicController {

    private final SubmitToeicService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<SubmitToeicDTO> findById(@PathVariable Long id) {
        SubmitToeicDTO toeic = service.findById(id);
        return ResponseDTO.<SubmitToeicDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(toeic)
                .message("SubmitToeic retrieved successfully")
                .build();
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<SubmitToeicDTO>> create(@Valid @RequestBody SubmitToeicDTO dto) {
        SubmitToeicDTO createdToeic = service.create(dto);

        ResponseDTO<SubmitToeicDTO> response = ResponseDTO.<SubmitToeicDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdToeic)
                .message("SubmitToeic created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitToeicDTO>> update(@PathVariable Long id, @Valid @RequestBody SubmitToeicDTO dto) {
        SubmitToeicDTO updatedToeic = service.update(dto, id);

        ResponseDTO<SubmitToeicDTO> response = ResponseDTO.<SubmitToeicDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedToeic)
                .message("SubmitToeic updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitToeicDTO>> patch(@PathVariable Long id, @RequestBody SubmitToeicDTO dto) {
        SubmitToeicDTO patchedToeic = service.patch(dto, id);

        ResponseDTO<SubmitToeicDTO> response = ResponseDTO.<SubmitToeicDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedToeic)
                .message("SubmitToeic updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "SubmitToeic deleted successfully" : "Failed to delete SubmitToeic")
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/by-test-and-user")
    public ResponseDTO<SubmitToeicDTO> findByToeicIdAndUserId(
            @RequestParam Long toeicId,
            @RequestParam Long userId
    ) {
        SubmitToeicDTO dto = service.findByToeicIdAndUserIdAndStatusFalse(toeicId, userId);
        return ResponseDTO.<SubmitToeicDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("Found successfully")
                .build();
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<Page<SubmitToeicDTO>> searchWithFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String sortFields,
            @RequestParam(required = false) Long userId,
            @ModelAttribute SubmitToeicFilterDTO filter) {

        Page<SubmitToeicDTO> result = service.searchWithFilters(page, size, sortFields, filter, userId);

        return ResponseDTO.<Page<SubmitToeicDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("SubmitToeic retrieved successfully with filters")
                .build();
    }
}
