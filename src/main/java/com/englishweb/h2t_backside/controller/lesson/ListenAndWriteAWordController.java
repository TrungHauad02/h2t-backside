package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.lesson.ListenAndWriteAWordDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.ListenAndWriteAWordService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/listen-and-write-a-word")
@AllArgsConstructor
public class ListenAndWriteAWordController {

    private final ListenAndWriteAWordService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<ListenAndWriteAWordDTO> findById(@PathVariable Long id) {
        ListenAndWriteAWordDTO listenAndWriteAWord = service.findById(id);

        return ResponseDTO.<ListenAndWriteAWordDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(listenAndWriteAWord)
                .message("Listen and write a word retrieved successfully")
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<ListenAndWriteAWordDTO> create(@Valid @RequestBody ListenAndWriteAWordDTO listenAndWriteAWordDTO) {
        ListenAndWriteAWordDTO createdListenAndWriteAWord = service.create(listenAndWriteAWordDTO);

        return ResponseDTO.<ListenAndWriteAWordDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdListenAndWriteAWord)
                .message("Listen and write a word created successfully")
                .build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<ListenAndWriteAWordDTO> update(@PathVariable Long id, @Valid @RequestBody ListenAndWriteAWordDTO listenAndWriteAWordDTO) {
        ListenAndWriteAWordDTO updatedListenAndWriteAWord = service.update(listenAndWriteAWordDTO, id);

        return ResponseDTO.<ListenAndWriteAWordDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedListenAndWriteAWord)
                .message("Listen and write a word updated successfully")
                .build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<ListenAndWriteAWordDTO> patch(@PathVariable Long id, @RequestBody ListenAndWriteAWordDTO listenAndWriteAWordDTO) {
        ListenAndWriteAWordDTO patchedListenAndWriteAWord = service.patch(listenAndWriteAWordDTO, id);

        return ResponseDTO.<ListenAndWriteAWordDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedListenAndWriteAWord)
                .message("Listen and write a word updated with patch successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        return ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Listen and write a word deleted successfully" : "Failed to delete listen and write a word")
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<ListenAndWriteAWordDTO>> findByListeningId(@RequestParam Long listeningId) {
        List<ListenAndWriteAWordDTO> listenAndWriteAWords = service.findByListeningId(listeningId);

        return ResponseDTO.<List<ListenAndWriteAWordDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(listenAndWriteAWords)
                .message("Listen and write a words retrieved successfully")
                .build();
    }
}
