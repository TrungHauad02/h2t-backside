package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.filter.ToeicFilterDTO;
import com.englishweb.h2t_backside.dto.test.ToeicDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.ToeicService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/toeic")
@AllArgsConstructor
public class ToeicController {

    private final ToeicService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<ToeicDTO> findById(@PathVariable Long id) {
        ToeicDTO dto = service.findById(id);
        return ResponseDTO.<ToeicDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("Toeic retrieved successfully")
                .build();
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<ToeicDTO>> create(@Valid @RequestBody ToeicDTO dto) {
        ToeicDTO createdToeic = service.create(dto);

        ResponseDTO<ToeicDTO> response = ResponseDTO.<ToeicDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdToeic)
                .message("Toeic created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<ToeicDTO>> update(@PathVariable Long id, @Valid @RequestBody ToeicDTO dto) {
        ToeicDTO updatedToeic = service.update(dto, id);

        ResponseDTO<ToeicDTO> response = ResponseDTO.<ToeicDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedToeic)
                .message("Toeic updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<ToeicDTO>> patch(@PathVariable Long id, @RequestBody ToeicDTO dto) {
        ToeicDTO patchedToeic = service.patch(dto, id);

        ResponseDTO<ToeicDTO> response = ResponseDTO.<ToeicDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedToeic)
                .message("Toeic updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Toeic deleted successfully" : "Failed to delete Toeic")
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<Page<ToeicDTO>> searchToeicWithFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String sortFields,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long ownerId,
            @ModelAttribute ToeicFilterDTO filter) {

        Page<ToeicDTO> results = service.searchWithFilters(page, size, sortFields, filter, userId,ownerId);

        return ResponseDTO.<Page<ToeicDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(results)
                .message("Toeic results retrieved successfully with filters")
                .build();
    }
    @GetMapping("/{id}/verify")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> verify(@PathVariable Long id) {
        boolean result = service.verifyValidToeic(id);
        if (result) {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Toeic verified successfully")
                    .build();
        } else {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Toeic not valid")
                    .build();
        }
    }

}
