package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.SubmitTestSpeakingDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.SubmitTestSpeakingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/submit-test-speaking")
@AllArgsConstructor
public class SubmitTestSpeakingController {

    private final SubmitTestSpeakingService service;
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<SubmitTestSpeakingDTO> findById(@PathVariable Long id) {
        SubmitTestSpeakingDTO dto = service.findById(id);
        return ResponseDTO.<SubmitTestSpeakingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("SubmitTestSpeaking retrieved successfully")
                .build();
    }

    @GetMapping("/by-submit-test/{submitTestId}/question/{questionId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<SubmitTestSpeakingDTO>> findBySubmitTestIdAndQuestionId(
            @PathVariable Long submitTestId,
            @PathVariable Long questionId) {
        List<SubmitTestSpeakingDTO> result = service.findBySubmitTestIdAndQuestionId(submitTestId, questionId);
        return ResponseDTO.<List<SubmitTestSpeakingDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("SubmitTestSpeaking retrieved by submitTestId and questionId")
                .build();
    }

    @PostMapping("/by-submit-test/{submitTestId}/questions")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<SubmitTestSpeakingDTO>> findBySubmitTestIdAndQuestionIds(
            @PathVariable Long submitTestId,
            @RequestBody List<Long> questionIds) {
        List<SubmitTestSpeakingDTO> result = service.findBySubmitTestIdAndQuestionIds(submitTestId, questionIds);
        return ResponseDTO.<List<SubmitTestSpeakingDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("SubmitTestSpeaking retrieved by submitTestId and questionIds")
                .build();
    }


    @PostMapping
    public ResponseEntity<ResponseDTO<SubmitTestSpeakingDTO>> create(@Valid @RequestBody SubmitTestSpeakingDTO dto) {
        SubmitTestSpeakingDTO createdSpeaking = service.create(dto);

        ResponseDTO<SubmitTestSpeakingDTO> response = ResponseDTO.<SubmitTestSpeakingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdSpeaking)
                .message("SubmitTestSpeaking created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitTestSpeakingDTO>> update(@PathVariable Long id, @Valid @RequestBody SubmitTestSpeakingDTO dto) {
        SubmitTestSpeakingDTO updatedSpeaking = service.update(dto, id);

        ResponseDTO<SubmitTestSpeakingDTO> response = ResponseDTO.<SubmitTestSpeakingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedSpeaking)
                .message("SubmitTestSpeaking updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitTestSpeakingDTO>> patch(@PathVariable Long id, @RequestBody SubmitTestSpeakingDTO dto) {
        SubmitTestSpeakingDTO patchedSpeaking = service.patch(dto, id);

        ResponseDTO<SubmitTestSpeakingDTO> response = ResponseDTO.<SubmitTestSpeakingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedSpeaking)
                .message("SubmitTestSpeaking updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "SubmitTestSpeaking deleted successfully" : "Failed to delete SubmitTestSpeaking")
                .build();
        return ResponseEntity.ok(response);
    }
}
