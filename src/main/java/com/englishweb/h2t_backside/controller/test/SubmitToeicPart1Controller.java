package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.SubmitToeicPart1DTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.SubmitToeicPart1Service;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/submit-toeic-part1s")
@AllArgsConstructor
public class SubmitToeicPart1Controller {

    private final SubmitToeicPart1Service service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<SubmitToeicPart1DTO> findById(@PathVariable Long id) {
        SubmitToeicPart1DTO dto = service.findById(id);
        return ResponseDTO.<SubmitToeicPart1DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("SubmitToeicPart1 retrieved successfully")
                .build();
    }


    @PostMapping
    public ResponseEntity<ResponseDTO<SubmitToeicPart1DTO>> create(@Valid @RequestBody SubmitToeicPart1DTO dto) {
        SubmitToeicPart1DTO createdPart1 = service.create(dto);

        ResponseDTO<SubmitToeicPart1DTO> response = ResponseDTO.<SubmitToeicPart1DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdPart1)
                .message("SubmitToeicPart1 created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitToeicPart1DTO>> update(@PathVariable Long id, @Valid @RequestBody SubmitToeicPart1DTO dto) {
        SubmitToeicPart1DTO updatedPart1 = service.update(dto, id);

        ResponseDTO<SubmitToeicPart1DTO> response = ResponseDTO.<SubmitToeicPart1DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedPart1)
                .message("SubmitToeicPart1 updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitToeicPart1DTO>> patch(@PathVariable Long id, @RequestBody SubmitToeicPart1DTO dto) {
        SubmitToeicPart1DTO patchedPart1 = service.patch(dto, id);

        ResponseDTO<SubmitToeicPart1DTO> response = ResponseDTO.<SubmitToeicPart1DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedPart1)
                .message("SubmitToeicPart1 updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "SubmitToeicPart1 deleted successfully" : "Failed to delete SubmitToeicPart1")
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/by-submit-toeic/{submitToeicId}/toeicPart1Id/{toeicPart1Id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<SubmitToeicPart1DTO>> findBySubmitToeicIdAndToeicPart1Id(
            @PathVariable Long submitToeicId,
            @PathVariable Long toeicPart1Id) {
        List<SubmitToeicPart1DTO> result = service.findBySubmitToeicIdAndToeicPart1Id(submitToeicId, toeicPart1Id);
        return ResponseDTO.<List<SubmitToeicPart1DTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("SubmitToeicPart1 retrieved by submitToeicId and testPart1Id")
                .build();
    }

    @PostMapping("/by-submit-toeic/{submitToeicId}/toeicPart1s")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<SubmitToeicPart1DTO>> findBySubmitToeicIdAndToeicPart1IdIn(
            @PathVariable Long submitToeicId,
            @RequestBody List<Long> toeicPart1Ids) {
        List<SubmitToeicPart1DTO> result = service.findBySubmitToeicIdAndToeicPart1IdIn(submitToeicId, toeicPart1Ids);
        return ResponseDTO.<List<SubmitToeicPart1DTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("SubmitToeicPart1 retrieved by submitToeicId and testPart1Ids")
                .build();
    }
}
