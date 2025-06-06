package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.QuestionDTO;
import com.englishweb.h2t_backside.dto.test.ToeicPart1DTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.model.test.ToeicPart1;
import com.englishweb.h2t_backside.service.test.ToeicPart1Service;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/toeic-part1")
@AllArgsConstructor
public class ToeicPart1Controller {

    private final ToeicPart1Service service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<ToeicPart1DTO> findById(@PathVariable Long id) {
        ToeicPart1DTO dto = service.findById(id);
        return ResponseDTO.<ToeicPart1DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("ToeicPart1 retrieved successfully")
                .build();
    }

    @PostMapping("/by-ids")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<ToeicPart1DTO>> getByIds(
            @RequestBody List<Long> ids,
            @RequestParam(required = false) Boolean status) {
        List<ToeicPart1DTO> result = service.findByIdsAndStatus(ids, status);
        return ResponseDTO.<List<ToeicPart1DTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("Toeic part 1 retrieved successfully")
                .build();
    }





    @PostMapping
    public ResponseEntity<ResponseDTO<ToeicPart1DTO>> create(@Valid @RequestBody ToeicPart1DTO dto) {
        ToeicPart1DTO createdToeicPart1 = service.create(dto);

        ResponseDTO<ToeicPart1DTO> response = ResponseDTO.<ToeicPart1DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdToeicPart1)
                .message("ToeicPart1 created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<ToeicPart1DTO>> update(@PathVariable Long id, @Valid @RequestBody ToeicPart1DTO dto) {
        ToeicPart1DTO updatedToeicPart1 = service.update(dto, id);

        ResponseDTO<ToeicPart1DTO> response = ResponseDTO.<ToeicPart1DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedToeicPart1)
                .message("ToeicPart1 updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<ToeicPart1DTO>> patch(@PathVariable Long id, @RequestBody ToeicPart1DTO dto) {
        ToeicPart1DTO patchedToeicPart1 = service.patch(dto, id);

        ResponseDTO<ToeicPart1DTO> response = ResponseDTO.<ToeicPart1DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedToeicPart1)
                .message("ToeicPart1 updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "ToeicPart1 deleted successfully" : "Failed to delete ToeicPart1")
                .build();
        return ResponseEntity.ok(response);
    }

}
