package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.lesson.PreparationMakeSentencesDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.PreparationMakeSentencesService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/preparation-make-sentences")
@AllArgsConstructor
public class PreparationMakeSentencesController {

    private final PreparationMakeSentencesService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<PreparationMakeSentencesDTO> findById(@PathVariable Long id) {
        PreparationMakeSentencesDTO preparationMakeSentences = service.findById(id);

        return ResponseDTO.<PreparationMakeSentencesDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(preparationMakeSentences)
                .message("Preparation make sentences retrieved successfully")
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<PreparationMakeSentencesDTO> create(@Valid @RequestBody PreparationMakeSentencesDTO preparationMakeSentencesDTO) {
        PreparationMakeSentencesDTO createdPreparationMakeSentences = service.create(preparationMakeSentencesDTO);

        return ResponseDTO.<PreparationMakeSentencesDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdPreparationMakeSentences)
                .message("Preparation make sentences created successfully")
                .build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<PreparationMakeSentencesDTO> update(@PathVariable Long id, @Valid @RequestBody PreparationMakeSentencesDTO preparationMakeSentencesDTO) {
        PreparationMakeSentencesDTO updatedPreparationMakeSentences = service.update(preparationMakeSentencesDTO, id);

        return ResponseDTO.<PreparationMakeSentencesDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedPreparationMakeSentences)
                .message("Preparation make sentences updated successfully")
                .build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<PreparationMakeSentencesDTO> patch(@PathVariable Long id, @RequestBody PreparationMakeSentencesDTO preparationMakeSentencesDTO) {
        PreparationMakeSentencesDTO patchedPreparationMakeSentences = service.patch(preparationMakeSentencesDTO, id);

        return ResponseDTO.<PreparationMakeSentencesDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedPreparationMakeSentences)
                .message("Preparation make sentences updated with patch successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        return ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Preparation make sentences deleted successfully" : "Failed to delete preparation make sentences")
                .build();
    }
}
