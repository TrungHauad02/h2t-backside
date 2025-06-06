package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.ToeicPart3_4DTO;
import com.englishweb.h2t_backside.dto.test.ToeicPart6DTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.ToeicPart6Service;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/toeic-part6")
@AllArgsConstructor
public class ToeicPart6Controller {

    private final ToeicPart6Service service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<ToeicPart6DTO> findById(@PathVariable Long id) {
        ToeicPart6DTO dto = service.findById(id);
        return ResponseDTO.<ToeicPart6DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("ToeicPart6 retrieved successfully")
                .build();
    }
    @PostMapping("/by-ids")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<ToeicPart6DTO>> getByIds(
            @RequestBody List<Long> ids,
            @RequestParam(required = false) Boolean status) {
        List<ToeicPart6DTO> result = service.findByIdsAndStatus(ids, status);
        return ResponseDTO.<List<ToeicPart6DTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("Toeic part 6 retrieved successfully")
                .build();
    }



    @PostMapping
    public ResponseEntity<ResponseDTO<ToeicPart6DTO>> create(@Valid @RequestBody ToeicPart6DTO dto) {
        ToeicPart6DTO createdToeicPart6 = service.create(dto);

        ResponseDTO<ToeicPart6DTO> response = ResponseDTO.<ToeicPart6DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdToeicPart6)
                .message("ToeicPart6 created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<ToeicPart6DTO>> update(@PathVariable Long id, @Valid @RequestBody ToeicPart6DTO dto) {
        ToeicPart6DTO updatedToeicPart6 = service.update(dto, id);

        ResponseDTO<ToeicPart6DTO> response = ResponseDTO.<ToeicPart6DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedToeicPart6)
                .message("ToeicPart6 updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<ToeicPart6DTO>> patch(@PathVariable Long id, @RequestBody ToeicPart6DTO dto) {
        ToeicPart6DTO patchedToeicPart6 = service.patch(dto, id);

        ResponseDTO<ToeicPart6DTO> response = ResponseDTO.<ToeicPart6DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedToeicPart6)
                .message("ToeicPart6 updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "ToeicPart6 deleted successfully" : "Failed to delete ToeicPart6")
                .build();
        return ResponseEntity.ok(response);
    }
}
