package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.dto.test.ToeicAnswerDTO;
import com.englishweb.h2t_backside.service.test.ToeicAnswerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/toeic-answers")
@AllArgsConstructor
public class ToeicAnswerController {

    private final ToeicAnswerService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<ToeicAnswerDTO> findById(@PathVariable Long id) {
        ToeicAnswerDTO dto = service.findById(id);
        return ResponseDTO.<ToeicAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("ToeicAnswer retrieved successfully")
                .build();
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<ToeicAnswerDTO>> create(@Valid @RequestBody ToeicAnswerDTO dto) {
        ToeicAnswerDTO createdAnswer = service.create(dto);
        return ResponseEntity.ok(ResponseDTO.<ToeicAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdAnswer)
                .message("ToeicAnswer created successfully")
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<ToeicAnswerDTO>> update(@PathVariable Long id, @Valid @RequestBody ToeicAnswerDTO dto) {
        ToeicAnswerDTO updatedAnswer = service.update(dto, id);
        return ResponseEntity.ok(ResponseDTO.<ToeicAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedAnswer)
                .message("ToeicAnswer updated successfully")
                .build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<ToeicAnswerDTO>> patch(@PathVariable Long id, @RequestBody ToeicAnswerDTO dto) {
        ToeicAnswerDTO patchedAnswer = service.patch(dto, id);
        return ResponseEntity.ok(ResponseDTO.<ToeicAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedAnswer)
                .message("ToeicAnswer updated with patch successfully")
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);
        return ResponseEntity.ok(ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "ToeicAnswer deleted successfully" : "Failed to delete ToeicAnswer")
                .build());
    }
}
