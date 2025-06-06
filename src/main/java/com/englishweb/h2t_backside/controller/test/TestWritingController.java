package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.TestReadingDTO;
import com.englishweb.h2t_backside.dto.test.TestWritingDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.TestWritingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/test-writings")
@AllArgsConstructor
public class TestWritingController {

    private final TestWritingService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<TestWritingDTO> findById(@PathVariable Long id) {
        TestWritingDTO dto = service.findById(id);
        return ResponseDTO.<TestWritingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("TestWriting retrieved successfully")
                .build();
    }
    @PostMapping("/by-ids")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<TestWritingDTO>> getByIds(
            @RequestBody List<Long> ids,
            @RequestParam(required = false) Boolean status) {

        List<TestWritingDTO> result = service.findByIdsAndStatus(ids, status);
        return ResponseDTO.<List<TestWritingDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("Test Writing retrieved successfully")
                .build();
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<TestWritingDTO>> create(@Valid @RequestBody TestWritingDTO dto) {
        TestWritingDTO createdTestWriting = service.create(dto);

        ResponseDTO<TestWritingDTO> response = ResponseDTO.<TestWritingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdTestWriting)
                .message("TestWriting created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<TestWritingDTO>> update(@PathVariable Long id, @Valid @RequestBody TestWritingDTO dto) {
        TestWritingDTO updatedTestWriting = service.update(dto, id);

        ResponseDTO<TestWritingDTO> response = ResponseDTO.<TestWritingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedTestWriting)
                .message("TestWriting updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<TestWritingDTO>> patch(@PathVariable Long id, @RequestBody TestWritingDTO dto) {
        TestWritingDTO patchedTestWriting = service.patch(dto, id);

        ResponseDTO<TestWritingDTO> response = ResponseDTO.<TestWritingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedTestWriting)
                .message("TestWriting updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "TestWriting deleted successfully" : "Failed to delete test writing")
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}/verify")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> verify(@PathVariable Long id) {
        boolean result = service.verifyValidTestWriting(id);
        if (result) {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Test writing successfully")
                    .build();
        } else {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Test writing not valid")
                    .build();
        }
    }
}
