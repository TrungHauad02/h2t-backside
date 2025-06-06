package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.ToeicPart2DTO;
import com.englishweb.h2t_backside.dto.test.ToeicPart3_4DTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.ToeicPart3_4Service;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/toeic-part3-4")
@AllArgsConstructor
public class ToeicPart3_4Controller {

    private final ToeicPart3_4Service service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<ToeicPart3_4DTO> findById(@PathVariable Long id) {
        ToeicPart3_4DTO dto = service.findById(id);
        return ResponseDTO.<ToeicPart3_4DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("ToeicPart3_4 retrieved successfully")
                .build();
    }
    @PostMapping("/by-ids")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<ToeicPart3_4DTO>> getByIds(
            @RequestBody List<Long> ids,
            @RequestParam(required = false) Boolean status) {
        List<ToeicPart3_4DTO> result = service.findByIdsAndStatus(ids, status);
        return ResponseDTO.<List<ToeicPart3_4DTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("Toeic part 3 4 retrieved successfully")
                .build();
    }


    @PostMapping
    public ResponseEntity<ResponseDTO<ToeicPart3_4DTO>> create(@Valid @RequestBody ToeicPart3_4DTO dto) {
        ToeicPart3_4DTO createdToeicPart3_4 = service.create(dto);

        ResponseDTO<ToeicPart3_4DTO> response = ResponseDTO.<ToeicPart3_4DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdToeicPart3_4)
                .message("ToeicPart3_4 created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<ToeicPart3_4DTO>> update(@PathVariable Long id, @Valid @RequestBody ToeicPart3_4DTO dto) {
        ToeicPart3_4DTO updatedToeicPart3_4 = service.update(dto, id);

        ResponseDTO<ToeicPart3_4DTO> response = ResponseDTO.<ToeicPart3_4DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedToeicPart3_4)
                .message("ToeicPart3_4 updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<ToeicPart3_4DTO>> patch(@PathVariable Long id, @RequestBody ToeicPart3_4DTO dto) {
        ToeicPart3_4DTO patchedToeicPart3_4 = service.patch(dto, id);

        ResponseDTO<ToeicPart3_4DTO> response = ResponseDTO.<ToeicPart3_4DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedToeicPart3_4)
                .message("ToeicPart3_4 updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "ToeicPart3_4 deleted successfully" : "Failed to delete ToeicPart3_4")
                .build();
        return ResponseEntity.ok(response);
    }
}
