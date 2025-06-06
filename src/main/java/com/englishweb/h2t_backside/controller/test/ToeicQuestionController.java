package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.dto.test.ToeicPart7DTO;
import com.englishweb.h2t_backside.dto.test.ToeicQuestionDTO;
import com.englishweb.h2t_backside.service.test.ToeicQuestionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/toeic-questions")
@AllArgsConstructor
public class ToeicQuestionController {

    private final ToeicQuestionService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<ToeicQuestionDTO> findById(@PathVariable Long id) {
        ToeicQuestionDTO dto = service.findById(id);
        return ResponseDTO.<ToeicQuestionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("ToeicQuestion retrieved successfully")
                .build();
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<ToeicQuestionDTO>> create(@Valid @RequestBody ToeicQuestionDTO dto) {
        ToeicQuestionDTO created = service.create(dto);
        return ResponseEntity.ok(ResponseDTO.<ToeicQuestionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(created)
                .message("ToeicQuestion created successfully")
                .build());
    }

    @PostMapping("/by-ids")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<ToeicQuestionDTO>> getByIds(
            @RequestBody List<Long> ids,
            @RequestParam(required = false) Boolean status) {
        List<ToeicQuestionDTO> result = service.findByIdsAndStatus(ids, status);
        return ResponseDTO.<List<ToeicQuestionDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("Toeic question retrieved successfully")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<ToeicQuestionDTO>> update(@PathVariable Long id, @Valid @RequestBody ToeicQuestionDTO dto) {
        ToeicQuestionDTO updated = service.update(dto, id);
        return ResponseEntity.ok(ResponseDTO.<ToeicQuestionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updated)
                .message("ToeicQuestion updated successfully")
                .build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<ToeicQuestionDTO>> patch(@PathVariable Long id, @RequestBody ToeicQuestionDTO dto) {
        ToeicQuestionDTO patched = service.patch(dto, id);
        return ResponseEntity.ok(ResponseDTO.<ToeicQuestionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patched)
                .message("ToeicQuestion updated with patch successfully")
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);
        return ResponseEntity.ok(ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "ToeicQuestion deleted successfully" : "Failed to delete ToeicQuestion")
                .build());
    }
}
