package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.lesson.PreparationMatchWordSentencesDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.PreparationMatchWordSentencesService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/preparation-match-word-sentences")
@AllArgsConstructor
public class PreparationMatchWordSentencesController {

    private final PreparationMatchWordSentencesService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<PreparationMatchWordSentencesDTO> findById(@PathVariable Long id) {
        PreparationMatchWordSentencesDTO preparationMatchWordSentences = service.findById(id);

        return ResponseDTO.<PreparationMatchWordSentencesDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(preparationMatchWordSentences)
                .message("Preparation match word sentences retrieved successfully")
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<PreparationMatchWordSentencesDTO> create(@Valid @RequestBody PreparationMatchWordSentencesDTO preparationMatchWordSentencesDTO) {
        PreparationMatchWordSentencesDTO createdPreparationMatchWordSentences = service.create(preparationMatchWordSentencesDTO);

        return ResponseDTO.<PreparationMatchWordSentencesDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdPreparationMatchWordSentences)
                .message("Preparation match word sentences created successfully")
                .build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<PreparationMatchWordSentencesDTO> update(@PathVariable Long id, @Valid @RequestBody PreparationMatchWordSentencesDTO preparationMatchWordSentencesDTO) {
        PreparationMatchWordSentencesDTO updatedPreparationMatchWordSentences = service.update(preparationMatchWordSentencesDTO, id);

        return ResponseDTO.<PreparationMatchWordSentencesDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedPreparationMatchWordSentences)
                .message("Preparation match word sentences updated successfully")
                .build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<PreparationMatchWordSentencesDTO> patch(@PathVariable Long id, @RequestBody PreparationMatchWordSentencesDTO preparationMatchWordSentencesDTO) {
        PreparationMatchWordSentencesDTO patchedPreparationMatchWordSentences = service.patch(preparationMatchWordSentencesDTO, id);

        return ResponseDTO.<PreparationMatchWordSentencesDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedPreparationMatchWordSentences)
                .message("Preparation match word sentences updated with patch successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        return ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Preparation match word sentences deleted successfully" : "Failed to delete preparation match word sentences")
                .build();
    }
}
