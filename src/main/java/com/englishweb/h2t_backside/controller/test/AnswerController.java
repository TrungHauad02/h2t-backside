package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.AnswerDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.AnswerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/answers")
@AllArgsConstructor
public class AnswerController {

    private final AnswerService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<AnswerDTO> findById(@PathVariable Long id) {
        AnswerDTO dto = service.findById(id);
        return ResponseDTO.<AnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("Answer retrieved successfully")
                .build();
    }


    @PostMapping
    public ResponseEntity<ResponseDTO<AnswerDTO>> create(@Valid @RequestBody AnswerDTO dto) {
        AnswerDTO createdAnswer = service.create(dto);

        ResponseDTO<AnswerDTO> response = ResponseDTO.<AnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdAnswer)
                .message("Answer created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<AnswerDTO>> update(@PathVariable Long id, @Valid @RequestBody AnswerDTO dto) {
        AnswerDTO updatedAnswer = service.update(dto, id);

        ResponseDTO<AnswerDTO> response = ResponseDTO.<AnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedAnswer)
                .message("Answer updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<AnswerDTO>> patch(@PathVariable Long id, @RequestBody AnswerDTO dto) {
        AnswerDTO patchedAnswer = service.patch(dto, id);

        ResponseDTO<AnswerDTO> response = ResponseDTO.<AnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedAnswer)
                .message("Answer updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Answer deleted successfully" : "Failed to delete answer")
                .build();
        return ResponseEntity.ok(response);
    }
}
