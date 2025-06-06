package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.lesson.SpeakingConversationDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.SpeakingConversationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/speaking-conversations")
@AllArgsConstructor
public class SpeakingConversationController {

    private final SpeakingConversationService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<SpeakingConversationDTO> findById(@PathVariable Long id) {
        SpeakingConversationDTO speakingConversation = service.findById(id);

        return ResponseDTO.<SpeakingConversationDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(speakingConversation)
                .message("Speaking conversation retrieved successfully")
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<SpeakingConversationDTO> create(@Valid @RequestBody SpeakingConversationDTO speakingConversationDTO) {
        SpeakingConversationDTO createdSpeakingConversation = service.create(speakingConversationDTO);

        return ResponseDTO.<SpeakingConversationDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdSpeakingConversation)
                .message("Speaking conversation created successfully")
                .build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<SpeakingConversationDTO> update(@PathVariable Long id, @Valid @RequestBody SpeakingConversationDTO speakingConversationDTO) {
        SpeakingConversationDTO updatedSpeakingConversation = service.update(speakingConversationDTO, id);

        return ResponseDTO.<SpeakingConversationDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedSpeakingConversation)
                .message("Speaking conversation updated successfully")
                .build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<SpeakingConversationDTO> patch(@PathVariable Long id, @RequestBody SpeakingConversationDTO speakingConversationDTO) {
        SpeakingConversationDTO patchedSpeakingConversation = service.patch(speakingConversationDTO, id);

        return ResponseDTO.<SpeakingConversationDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedSpeakingConversation)
                .message("Speaking conversation updated with patch successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        return ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Speaking conversation deleted successfully" : "Failed to delete speaking conversation")
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<SpeakingConversationDTO>> findBySpeakingId(@RequestParam Long speakingId) {
        List<SpeakingConversationDTO> speakingConversations = service.findBySpeakingId(speakingId);

        return ResponseDTO.<List<SpeakingConversationDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(speakingConversations)
                .message("Speaking conversations retrieved successfully")
                .build();
    }
}
