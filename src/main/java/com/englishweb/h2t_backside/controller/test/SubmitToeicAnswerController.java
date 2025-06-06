package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.dto.test.SubmitToeicAnswerDTO;
import com.englishweb.h2t_backside.service.test.SubmitToeicAnswerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/submit-toeic-answers")
@AllArgsConstructor
public class SubmitToeicAnswerController {

    private final SubmitToeicAnswerService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<SubmitToeicAnswerDTO> findById(@PathVariable Long id) {
        SubmitToeicAnswerDTO dto = service.findById(id);
        return ResponseDTO.<SubmitToeicAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("SubmitToeicAnswer retrieved successfully")
                .build();
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<SubmitToeicAnswerDTO>> create(@Valid @RequestBody SubmitToeicAnswerDTO dto) {
        SubmitToeicAnswerDTO created = service.create(dto);
        return ResponseEntity.ok(ResponseDTO.<SubmitToeicAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(created)
                .message("SubmitToeicAnswer created successfully")
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitToeicAnswerDTO>> update(@PathVariable Long id, @Valid @RequestBody SubmitToeicAnswerDTO dto) {
        SubmitToeicAnswerDTO updated = service.update(dto, id);
        return ResponseEntity.ok(ResponseDTO.<SubmitToeicAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updated)
                .message("SubmitToeicAnswer updated successfully")
                .build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitToeicAnswerDTO>> patch(@PathVariable Long id, @RequestBody SubmitToeicAnswerDTO dto) {
        SubmitToeicAnswerDTO patched = service.patch(dto, id);
        return ResponseEntity.ok(ResponseDTO.<SubmitToeicAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patched)
                .message("SubmitToeicAnswer updated with patch successfully")
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);
        return ResponseEntity.ok(ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "SubmitToeicAnswer deleted successfully" : "Failed to delete SubmitToeicAnswer")
                .build());
    }
    @GetMapping("/by-submit-toeic/{submitToeicId}/question/{questionId}")
    public ResponseDTO<List<SubmitToeicAnswerDTO>> findBySubmitToeicIdAndQuestionId(
            @PathVariable Long submitToeicId,
            @PathVariable Long questionId) {
        List<SubmitToeicAnswerDTO> result = service.findBySubmitToeicIdAndQuestionId(submitToeicId, questionId);
        return ResponseDTO.<List<SubmitToeicAnswerDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("SubmitToeicAnswer retrieved by submitToeicId and questionId")
                .build();
    }

    @PostMapping("/by-submit-toeic/{submitToeicId}/questions")
    public ResponseDTO<List<SubmitToeicAnswerDTO>> findBySubmitToeicIdAndQuestionIdIn(
            @PathVariable Long submitToeicId,
            @RequestBody List<Long> questionIds) {
        List<SubmitToeicAnswerDTO> result = service.findBySubmitToeicIdAndQuestionIdIn(submitToeicId, questionIds);
        return ResponseDTO.<List<SubmitToeicAnswerDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("SubmitToeicAnswer retrieved by submitToeicId and questionIds")
                .build();
    }
}
