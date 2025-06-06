package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.SubmitCompetitionAnswerDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.SubmitCompetitionAnswerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/submit-competition-answers")
@AllArgsConstructor
public class SubmitCompetitionAnswerController {

    private final SubmitCompetitionAnswerService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<SubmitCompetitionAnswerDTO> findById(@PathVariable Long id) {
        SubmitCompetitionAnswerDTO dto = service.findById(id);
        return ResponseDTO.<SubmitCompetitionAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("SubmitCompetitionAnswer retrieved successfully")
                .build();
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<SubmitCompetitionAnswerDTO>> create(@Valid @RequestBody SubmitCompetitionAnswerDTO dto) {
        SubmitCompetitionAnswerDTO createdAnswer = service.create(dto);

        ResponseDTO<SubmitCompetitionAnswerDTO> response = ResponseDTO.<SubmitCompetitionAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdAnswer)
                .message("SubmitCompetitionAnswer created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitCompetitionAnswerDTO>> update(@PathVariable Long id, @Valid @RequestBody SubmitCompetitionAnswerDTO dto) {
        SubmitCompetitionAnswerDTO updatedAnswer = service.update(dto, id);

        ResponseDTO<SubmitCompetitionAnswerDTO> response = ResponseDTO.<SubmitCompetitionAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedAnswer)
                .message("SubmitCompetitionAnswer updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitCompetitionAnswerDTO>> patch(@PathVariable Long id, @RequestBody SubmitCompetitionAnswerDTO dto) {
        SubmitCompetitionAnswerDTO patchedAnswer = service.patch(dto, id);

        ResponseDTO<SubmitCompetitionAnswerDTO> response = ResponseDTO.<SubmitCompetitionAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedAnswer)
                .message("SubmitCompetitionAnswer updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "SubmitCompetitionAnswer deleted successfully" : "Failed to delete SubmitCompetitionAnswer")
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/by-submit-competition/{submitCompetitionId}/question/{questionId}")
    public ResponseDTO<List<SubmitCompetitionAnswerDTO>> findBySubmitCompetitionIdAndQuestionId(
            @PathVariable Long submitCompetitionId,
            @PathVariable Long questionId) {
        List<SubmitCompetitionAnswerDTO> result = service.findBySubmitCompetitionIdAndQuestionId(submitCompetitionId, questionId);
        return ResponseDTO.<List<SubmitCompetitionAnswerDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("SubmitCompetitionAnswer retrieved successfully")
                .build();
    }

    @PostMapping("/by-submit-competition/{submitCompetitionId}/questions")
    public ResponseDTO<List<SubmitCompetitionAnswerDTO>> findBySubmitCompetitionIdAndQuestionIds(
            @PathVariable Long submitCompetitionId,
            @RequestBody List<Long> questionIds) {
        List<SubmitCompetitionAnswerDTO> result = service.findBySubmitCompetitionIdAndQuestionIds(submitCompetitionId, questionIds);
        return ResponseDTO.<List<SubmitCompetitionAnswerDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("SubmitCompetitionAnswer retrieved successfully")
                .build();
    }
}
