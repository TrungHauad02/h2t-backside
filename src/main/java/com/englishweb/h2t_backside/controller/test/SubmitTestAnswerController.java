package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.SubmitTestAnswerDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.SubmitTestAnswerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/submit-test-answers")
@AllArgsConstructor
public class SubmitTestAnswerController {

    private final SubmitTestAnswerService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<SubmitTestAnswerDTO> findById(@PathVariable Long id) {
        SubmitTestAnswerDTO dto = service.findById(id);
        return ResponseDTO.<SubmitTestAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("SubmitTestAnswer retrieved successfully")
                .build();
    }

    @GetMapping("/by-submit-test/{submitTestId}/question/{questionId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<SubmitTestAnswerDTO>> findBySubmitTestIdAndQuestionId(
            @PathVariable Long submitTestId,
            @PathVariable Long questionId) {
        List<SubmitTestAnswerDTO> result = service.findBySubmitTestIdAndQuestionId(submitTestId, questionId);
        return ResponseDTO.<List<SubmitTestAnswerDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("Answers retrieved by submitTestId and questionId")
                .build();
    }

    @PostMapping("/by-submit-test/{submitTestId}/questions")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<SubmitTestAnswerDTO>> findBySubmitTestIdAndQuestionIds(
            @PathVariable Long submitTestId,
            @RequestBody List<Long> questionIds) {
        List<SubmitTestAnswerDTO> result = service.findBySubmitTestIdAndQuestionIds(submitTestId, questionIds);
        return ResponseDTO.<List<SubmitTestAnswerDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("Answers retrieved by submitTestId and questionIds")
                .build();
    }



    @PostMapping
    public ResponseEntity<ResponseDTO<SubmitTestAnswerDTO>> create(@Valid @RequestBody SubmitTestAnswerDTO dto) {
        SubmitTestAnswerDTO createdAnswer = service.create(dto);

        ResponseDTO<SubmitTestAnswerDTO> response = ResponseDTO.<SubmitTestAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdAnswer)
                .message("SubmitTestAnswer created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitTestAnswerDTO>> update(@PathVariable Long id, @Valid @RequestBody SubmitTestAnswerDTO dto) {
        SubmitTestAnswerDTO updatedAnswer = service.update(dto, id);

        ResponseDTO<SubmitTestAnswerDTO> response = ResponseDTO.<SubmitTestAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedAnswer)
                .message("SubmitTestAnswer updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitTestAnswerDTO>> patch(@PathVariable Long id, @RequestBody SubmitTestAnswerDTO dto) {
        SubmitTestAnswerDTO patchedAnswer = service.patch(dto, id);

        ResponseDTO<SubmitTestAnswerDTO> response = ResponseDTO.<SubmitTestAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedAnswer)
                .message("SubmitTestAnswer updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "SubmitTestAnswer deleted successfully" : "Failed to delete SubmitTestAnswer")
                .build();
        return ResponseEntity.ok(response);
    }
}
