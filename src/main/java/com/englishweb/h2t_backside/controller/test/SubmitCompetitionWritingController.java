package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.SubmitCompetitionWritingDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.SubmitCompetitionWritingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/submit-competition-writing")
@AllArgsConstructor
public class SubmitCompetitionWritingController {

    private final SubmitCompetitionWritingService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<SubmitCompetitionWritingDTO> findById(@PathVariable Long id) {
        SubmitCompetitionWritingDTO dto = service.findById(id);
        return ResponseDTO.<SubmitCompetitionWritingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("SubmitCompetitionWriting retrieved successfully")
                .build();
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<SubmitCompetitionWritingDTO>> create(@Valid @RequestBody SubmitCompetitionWritingDTO dto) {
        SubmitCompetitionWritingDTO createdWriting = service.create(dto);

        ResponseDTO<SubmitCompetitionWritingDTO> response = ResponseDTO.<SubmitCompetitionWritingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdWriting)
                .message("SubmitCompetitionWriting created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitCompetitionWritingDTO>> update(@PathVariable Long id, @Valid @RequestBody SubmitCompetitionWritingDTO dto) {
        SubmitCompetitionWritingDTO updatedWriting = service.update(dto, id);

        ResponseDTO<SubmitCompetitionWritingDTO> response = ResponseDTO.<SubmitCompetitionWritingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedWriting)
                .message("SubmitCompetitionWriting updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitCompetitionWritingDTO>> patch(@PathVariable Long id, @RequestBody SubmitCompetitionWritingDTO dto) {
        SubmitCompetitionWritingDTO patchedWriting = service.patch(dto, id);

        ResponseDTO<SubmitCompetitionWritingDTO> response = ResponseDTO.<SubmitCompetitionWritingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedWriting)
                .message("SubmitCompetitionWriting updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "SubmitCompetitionWriting deleted successfully" : "Failed to delete SubmitCompetitionWriting")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-submit-competition/{submitCompetitionId}/testwriting/{testWritingId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<SubmitCompetitionWritingDTO>> findBySubmitCompetitionIdAndTestWritingId(
            @PathVariable Long submitCompetitionId,
            @PathVariable Long testWritingId) {
        List<SubmitCompetitionWritingDTO> result = service.findBySubmitCompetitionIdAndTestWritingId(submitCompetitionId, testWritingId);
        return ResponseDTO.<List<SubmitCompetitionWritingDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("SubmitCompetitionWriting retrieved by submitCompetitionId and testWritingId")
                .build();
    }

    @PostMapping("/by-submit-competition/{submitCompetitionId}/testwritings")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<SubmitCompetitionWritingDTO>> findBySubmitCompetitionIdAndTestWritingIdIn(
            @PathVariable Long submitCompetitionId,
            @RequestBody List<Long> testWritingIds) {
        List<SubmitCompetitionWritingDTO> result = service.findBySubmitCompetitionIdAndTestWritingIdIn(submitCompetitionId, testWritingIds);
        return ResponseDTO.<List<SubmitCompetitionWritingDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("SubmitCompetitionWriting retrieved by submitCompetitionId and testWritingIds")
                .build();
    }
}
