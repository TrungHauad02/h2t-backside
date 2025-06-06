package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.lesson.WritingAnswerDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.WritingAnswerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/writing-answers")
@AllArgsConstructor
public class WritingAnswerController {

    private final WritingAnswerService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<WritingAnswerDTO> findById(@PathVariable Long id) {
        WritingAnswerDTO writingAnswer = service.findById(id);

        return ResponseDTO.<WritingAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(writingAnswer)
                .message("Writing answer retrieved successfully")
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<WritingAnswerDTO> create(@Valid @RequestBody WritingAnswerDTO writingAnswerDTO) {
        WritingAnswerDTO createdWritingAnswer = service.create(writingAnswerDTO);

        return ResponseDTO.<WritingAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdWritingAnswer)
                .message("Writing answer created successfully")
                .build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<WritingAnswerDTO> update(@PathVariable Long id, @Valid @RequestBody WritingAnswerDTO writingAnswerDTO) {
        WritingAnswerDTO updatedWritingAnswer = service.update(writingAnswerDTO, id);

        return ResponseDTO.<WritingAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedWritingAnswer)
                .message("Writing answer updated successfully")
                .build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<WritingAnswerDTO> patch(@PathVariable Long id, @RequestBody WritingAnswerDTO writingAnswerDTO) {
        WritingAnswerDTO patchedWritingAnswer = service.patch(writingAnswerDTO, id);

        return ResponseDTO.<WritingAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedWritingAnswer)
                .message("Writing answer updated with patch successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        return ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Writing answer deleted successfully" : "Failed to delete writing answer")
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<WritingAnswerDTO>> findByWritingId(@RequestParam Long writingId) {
        List<WritingAnswerDTO> writingAnswers = service.findByWritingId(writingId);

        return ResponseDTO.<List<WritingAnswerDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(writingAnswers)
                .message("Writing answers retrieved successfully")
                .build();
    }
}
