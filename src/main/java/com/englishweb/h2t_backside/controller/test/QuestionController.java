package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.QuestionDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.dto.test.ToeicQuestionDTO;
import com.englishweb.h2t_backside.service.test.QuestionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/questions")
@AllArgsConstructor
public class QuestionController {

    private final QuestionService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<QuestionDTO> findById(@PathVariable Long id) {
        QuestionDTO dto = service.findById(id);
        return ResponseDTO.<QuestionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("Question retrieved successfully")
                .build();
    }
    @PostMapping("/by-ids")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<QuestionDTO>> getByIds(
            @RequestBody List<Long> ids,
            @RequestParam(required = false) Boolean status) {
        List<QuestionDTO> result = service.findByIdsAndStatus(ids, status);
        return ResponseDTO.<List<QuestionDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("Questions retrieved successfully")
                .build();
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<QuestionDTO>> create(@Valid @RequestBody QuestionDTO dto) {
        QuestionDTO createdQuestion = service.create(dto);

        ResponseDTO<QuestionDTO> response = ResponseDTO.<QuestionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdQuestion)
                .message("Question created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<QuestionDTO>> update(@PathVariable Long id, @Valid @RequestBody QuestionDTO dto) {
        QuestionDTO updatedQuestion = service.update(dto, id);

        ResponseDTO<QuestionDTO> response = ResponseDTO.<QuestionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedQuestion)
                .message("Question updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<QuestionDTO>> patch(@PathVariable Long id, @RequestBody QuestionDTO dto) {
        QuestionDTO patchedQuestion = service.patch(dto, id);

        ResponseDTO<QuestionDTO> response = ResponseDTO.<QuestionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedQuestion)
                .message("Question updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Question deleted successfully" : "Failed to delete question")
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}/verify")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> verify(@PathVariable Long id) {
        boolean result = service.verifyValidQuestion(id);
        if (result) {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Question test verified successfully")
                    .build();
        } else {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Question test not valid")
                    .build();
        }
    }
}
