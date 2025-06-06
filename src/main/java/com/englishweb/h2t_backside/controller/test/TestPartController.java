package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.QuestionDTO;
import com.englishweb.h2t_backside.dto.test.TestPartDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.TestPartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/test-parts")
@AllArgsConstructor
public class TestPartController {

    private final TestPartService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<TestPartDTO> findById(@PathVariable Long id) {
        TestPartDTO dto = service.findById(id);
        return ResponseDTO.<TestPartDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("TestPart retrieved successfully")
                .build();
    }
    @PostMapping("/by-ids")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<TestPartDTO>> getByIds(@RequestBody List<Long> ids) {
        List<TestPartDTO> result = service.findByIds(ids);
        return ResponseDTO.<List<TestPartDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("TestPart retrieved successfully")
                .build();
    }
    @GetMapping("/questions")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<QuestionDTO>> findQuestionByTestId(
            @RequestParam Long testId,
            @RequestParam(required = false) Boolean status) {

        List<QuestionDTO> questions = service.findQuestionByTestId(testId, status);

        return ResponseDTO.<List<QuestionDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(questions)
                .message("Questions retrieved successfully for the listening")
                .build();
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<TestPartDTO>> create(@Valid @RequestBody TestPartDTO dto) {
        TestPartDTO createdTestPart = service.create(dto);

        ResponseDTO<TestPartDTO> response = ResponseDTO.<TestPartDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdTestPart)
                .message("TestPart created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<TestPartDTO>> update(@PathVariable Long id, @Valid @RequestBody TestPartDTO dto) {
        TestPartDTO updatedTestPart = service.update(dto, id);

        ResponseDTO<TestPartDTO> response = ResponseDTO.<TestPartDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedTestPart)
                .message("TestPart updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<TestPartDTO>> patch(@PathVariable Long id, @RequestBody TestPartDTO dto) {
        TestPartDTO patchedTestPart = service.patch(dto, id);

        ResponseDTO<TestPartDTO> response = ResponseDTO.<TestPartDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedTestPart)
                .message("TestPart updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "TestPart deleted successfully" : "Failed to delete test part")
                .build();
        return ResponseEntity.ok(response);
    }
}
