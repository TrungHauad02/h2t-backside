package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.lesson.LessonQuestionDTO;
import com.englishweb.h2t_backside.dto.test.QuestionDTO;
import com.englishweb.h2t_backside.dto.test.TestListeningDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.TestListeningService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
    @RequestMapping("/api/test-listenings")
@AllArgsConstructor
public class TestListeningController {

    private final TestListeningService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<TestListeningDTO> findById(@PathVariable Long id) {
        TestListeningDTO dto = service.findById(id);
        return ResponseDTO.<TestListeningDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("TestListening retrieved successfully")
                .build();
    }
    @PostMapping("/by-ids")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<TestListeningDTO>> getByIds(
            @RequestBody List<Long> ids,
            @RequestParam(required = false) Boolean status) {

        List<TestListeningDTO> result = service.findByIdsAndStatus(ids, status);
        return ResponseDTO.<List<TestListeningDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("TestListening retrieved successfully")
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
    public ResponseEntity<ResponseDTO<TestListeningDTO>> create(@Valid @RequestBody TestListeningDTO dto) {
        TestListeningDTO createdListening = service.create(dto);

        ResponseDTO<TestListeningDTO> response = ResponseDTO.<TestListeningDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdListening)
                .message("TestListening created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<TestListeningDTO>> update(@PathVariable Long id, @Valid @RequestBody TestListeningDTO dto) {
        TestListeningDTO updatedListening = service.update(dto, id);

        ResponseDTO<TestListeningDTO> response = ResponseDTO.<TestListeningDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedListening)
                .message("TestListening updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<TestListeningDTO>> patch(@PathVariable Long id, @RequestBody TestListeningDTO dto) {
        TestListeningDTO patchedListening = service.patch(dto, id);

        ResponseDTO<TestListeningDTO> response = ResponseDTO.<TestListeningDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedListening)
                .message("TestListening updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "TestListening deleted successfully" : "Failed to delete TestListening")
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}/verify")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> verify(@PathVariable Long id) {
        boolean result = service.verifyValidTestListening(id);
        if (result) {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Test listening successfully")
                    .build();
        } else {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Test listening not valid")
                    .build();
        }
    }
}
