package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.ToeicPart1DTO;
import com.englishweb.h2t_backside.dto.test.ToeicPart2DTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.model.test.ToeicPart2;
import com.englishweb.h2t_backside.service.test.ToeicPart2Service;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/toeic-part2")
@AllArgsConstructor
public class ToeicPart2Controller {

    private final ToeicPart2Service service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<ToeicPart2DTO> findById(@PathVariable Long id) {
        ToeicPart2DTO dto = service.findById(id);
        return ResponseDTO.<ToeicPart2DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("ToeicPart2 retrieved successfully")
                .build();
    }
    @PostMapping("/by-ids")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<ToeicPart2DTO>> getByIds(
            @RequestBody List<Long> ids,
            @RequestParam(required = false) Boolean status) {
        List<ToeicPart2DTO> result = service.findByIdsAndStatus(ids, status);
        return ResponseDTO.<List<ToeicPart2DTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("Toeic part 2 retrieved successfully")
                .build();
    }



    @PostMapping
    public ResponseEntity<ResponseDTO<ToeicPart2DTO>> create(@Valid @RequestBody ToeicPart2DTO dto) {
        ToeicPart2DTO createdToeicPart2 = service.create(dto);

        ResponseDTO<ToeicPart2DTO> response = ResponseDTO.<ToeicPart2DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdToeicPart2)
                .message("ToeicPart2 created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<ToeicPart2DTO>> update(@PathVariable Long id, @Valid @RequestBody ToeicPart2DTO dto) {
        ToeicPart2DTO updatedToeicPart2 = service.update(dto, id);

        ResponseDTO<ToeicPart2DTO> response = ResponseDTO.<ToeicPart2DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedToeicPart2)
                .message("ToeicPart2 updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<ToeicPart2DTO>> patch(@PathVariable Long id, @RequestBody ToeicPart2DTO dto) {
        ToeicPart2DTO patchedToeicPart2 = service.patch(dto, id);

        ResponseDTO<ToeicPart2DTO> response = ResponseDTO.<ToeicPart2DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedToeicPart2)
                .message("ToeicPart2 updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "ToeicPart2 deleted successfully" : "Failed to delete ToeicPart2")
                .build();
        return ResponseEntity.ok(response);
    }
}
