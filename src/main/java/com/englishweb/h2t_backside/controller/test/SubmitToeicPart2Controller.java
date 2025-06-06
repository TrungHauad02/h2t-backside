package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.SubmitToeicPart2DTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.SubmitToeicPart2Service;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/submit-toeic-part2s")
@AllArgsConstructor
public class SubmitToeicPart2Controller {

    private final SubmitToeicPart2Service service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<SubmitToeicPart2DTO> findById(@PathVariable Long id) {
        SubmitToeicPart2DTO dto = service.findById(id);
        return ResponseDTO.<SubmitToeicPart2DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("SubmitToeicPart2 retrieved successfully")
                .build();
    }


    @PostMapping
    public ResponseEntity<ResponseDTO<SubmitToeicPart2DTO>> create(@Valid @RequestBody SubmitToeicPart2DTO dto) {
        SubmitToeicPart2DTO createdPart2 = service.create(dto);

        ResponseDTO<SubmitToeicPart2DTO> response = ResponseDTO.<SubmitToeicPart2DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdPart2)
                .message("SubmitToeicPart2 created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitToeicPart2DTO>> update(@PathVariable Long id, @Valid @RequestBody SubmitToeicPart2DTO dto) {
        SubmitToeicPart2DTO updatedPart2 = service.update(dto, id);

        ResponseDTO<SubmitToeicPart2DTO> response = ResponseDTO.<SubmitToeicPart2DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedPart2)
                .message("SubmitToeicPart2 updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitToeicPart2DTO>> patch(@PathVariable Long id, @RequestBody SubmitToeicPart2DTO dto) {
        SubmitToeicPart2DTO patchedPart2 = service.patch(dto, id);

        ResponseDTO<SubmitToeicPart2DTO> response = ResponseDTO.<SubmitToeicPart2DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedPart2)
                .message("SubmitToeicPart2 updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "SubmitToeicPart2 deleted successfully" : "Failed to delete SubmitToeicPart2")
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/by-submit-toeic/{submitToeicId}/toeicpart2/{toeicPart2Id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<SubmitToeicPart2DTO>> findBySubmitToeicIdAndTestPart2Id(
            @PathVariable Long submitToeicId,
            @PathVariable Long toeicPart2Id) {
        List<SubmitToeicPart2DTO> result = service.findBySubmitToeicIdAndToeicPart2Id(submitToeicId, toeicPart2Id);
        return ResponseDTO.<List<SubmitToeicPart2DTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("SubmitToeicPart2 retrieved by submitToeicId and testPart2Id")
                .build();
    }

    @PostMapping("/by-submit-toeic/{submitToeicId}/toeicPart2Ids")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<SubmitToeicPart2DTO>> findBySubmitToeicIdAndToeicPart2IdIn(
            @PathVariable Long submitToeicId,
            @RequestBody List<Long> toeicPart2Ids) {
        List<SubmitToeicPart2DTO> result = service.findBySubmitToeicIdAndToeicPart2IdIn(submitToeicId, toeicPart2Ids);
        return ResponseDTO.<List<SubmitToeicPart2DTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("SubmitToeicPart2 retrieved by submitToeicId and testPart2Ids")
                .build();
    }
}
