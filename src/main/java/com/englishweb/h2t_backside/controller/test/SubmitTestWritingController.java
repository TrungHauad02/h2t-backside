package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.SubmitTestWritingDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.SubmitTestWritingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/submit-test-writing")
@AllArgsConstructor
public class SubmitTestWritingController {

    private final SubmitTestWritingService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<SubmitTestWritingDTO> findById(@PathVariable Long id) {
        SubmitTestWritingDTO dto = service.findById(id);
        return ResponseDTO.<SubmitTestWritingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("SubmitTestWriting retrieved successfully")
                .build();
    }
    @GetMapping("/by-submit-test/{submitTestId}/testwriting/{testWritingId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<SubmitTestWritingDTO>> findBySubmitTestIdAndTestWritingId(
            @PathVariable Long submitTestId,
            @PathVariable Long testWritingId) {
        List<SubmitTestWritingDTO> result = service.findBySubmitTestIdAndTestWritingId(submitTestId, testWritingId);
        return ResponseDTO.<List<SubmitTestWritingDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("SubmitTestWriting retrieved by submitTestId and testWritingId")
                .build();
    }

    @PostMapping("/by-submit-test/{submitTestId}/testwritings")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<SubmitTestWritingDTO>> findBySubmitTestIdAndTestWriting_IdIn(
            @PathVariable Long submitTestId,
            @RequestBody List<Long> testWritingIds) {
        List<SubmitTestWritingDTO> result = service.findBySubmitTestIdAndTestWriting_IdIn(submitTestId, testWritingIds);
        return ResponseDTO.<List<SubmitTestWritingDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("SubmitTestWriting retrieved by submitTestId and testWritingIds")
                .build();
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<SubmitTestWritingDTO>> create(@Valid @RequestBody SubmitTestWritingDTO dto) {
        SubmitTestWritingDTO createdWriting = service.create(dto);

        ResponseDTO<SubmitTestWritingDTO> response = ResponseDTO.<SubmitTestWritingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdWriting)
                .message("SubmitTestWriting created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitTestWritingDTO>> update(@PathVariable Long id, @Valid @RequestBody SubmitTestWritingDTO dto) {
        SubmitTestWritingDTO updatedWriting = service.update(dto, id);

        ResponseDTO<SubmitTestWritingDTO> response = ResponseDTO.<SubmitTestWritingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedWriting)
                .message("SubmitTestWriting updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitTestWritingDTO>> patch(@PathVariable Long id, @RequestBody SubmitTestWritingDTO dto) {
        SubmitTestWritingDTO patchedWriting = service.patch(dto, id);

        ResponseDTO<SubmitTestWritingDTO> response = ResponseDTO.<SubmitTestWritingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedWriting)
                .message("SubmitTestWriting updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "SubmitTestWriting deleted successfully" : "Failed to delete SubmitTestWriting")
                .build();
        return ResponseEntity.ok(response);
    }
}
