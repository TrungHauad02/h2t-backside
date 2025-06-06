package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.SubmitCompetitionSpeakingDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.SubmitCompetitionSpeakingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/submit-competition-speaking")
@AllArgsConstructor
public class SubmitCompetitionSpeakingController {

    private final SubmitCompetitionSpeakingService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<SubmitCompetitionSpeakingDTO> findById(@PathVariable Long id) {
        SubmitCompetitionSpeakingDTO dto = service.findById(id);
        return ResponseDTO.<SubmitCompetitionSpeakingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("SubmitCompetitionSpeaking retrieved successfully")
                .build();
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<SubmitCompetitionSpeakingDTO>> create(@Valid @RequestBody SubmitCompetitionSpeakingDTO dto) {
        SubmitCompetitionSpeakingDTO createdSpeaking = service.create(dto);

        ResponseDTO<SubmitCompetitionSpeakingDTO> response = ResponseDTO.<SubmitCompetitionSpeakingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdSpeaking)
                .message("SubmitCompetitionSpeaking created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitCompetitionSpeakingDTO>> update(@PathVariable Long id, @Valid @RequestBody SubmitCompetitionSpeakingDTO dto) {
        SubmitCompetitionSpeakingDTO updatedSpeaking = service.update(dto, id);

        ResponseDTO<SubmitCompetitionSpeakingDTO> response = ResponseDTO.<SubmitCompetitionSpeakingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedSpeaking)
                .message("SubmitCompetitionSpeaking updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitCompetitionSpeakingDTO>> patch(@PathVariable Long id, @RequestBody SubmitCompetitionSpeakingDTO dto) {
        SubmitCompetitionSpeakingDTO patchedSpeaking = service.patch(dto, id);

        ResponseDTO<SubmitCompetitionSpeakingDTO> response = ResponseDTO.<SubmitCompetitionSpeakingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedSpeaking)
                .message("SubmitCompetitionSpeaking updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "SubmitCompetitionSpeaking deleted successfully" : "Failed to delete SubmitCompetitionSpeaking")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-submit-competition/{submitCompetitionId}/question/{questionId}")
    public ResponseDTO<List<SubmitCompetitionSpeakingDTO>> findBySubmitCompetitionIdAndQuestionId(
            @PathVariable Long submitCompetitionId,
            @PathVariable Long questionId) {
        List<SubmitCompetitionSpeakingDTO> result = service.findBySubmitCompetitionIdAndQuestionId(submitCompetitionId, questionId);
        return ResponseDTO.<List<SubmitCompetitionSpeakingDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("SubmitCompetitionSpeaking retrieved successfully")
                .build();
    }

    @PostMapping("/by-submit-competition/{submitCompetitionId}/questions")
    public ResponseDTO<List<SubmitCompetitionSpeakingDTO>> findBySubmitCompetitionIdAndQuestionIds(
            @PathVariable Long submitCompetitionId,
            @RequestBody List<Long> questionIds) {
        List<SubmitCompetitionSpeakingDTO> result = service.findBySubmitCompetitionIdAndQuestionIds(submitCompetitionId, questionIds);
        return ResponseDTO.<List<SubmitCompetitionSpeakingDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("SubmitCompetitionSpeaking retrieved successfully")
                .build();
    }
}
