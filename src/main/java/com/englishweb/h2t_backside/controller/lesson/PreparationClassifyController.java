package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.lesson.PreparationClassifyDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.PreparationClassifyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/preparation-classifies")
@AllArgsConstructor
public class PreparationClassifyController {

    private final PreparationClassifyService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<PreparationClassifyDTO> findById(@PathVariable Long id) {
        PreparationClassifyDTO preparationClassify = service.findById(id);

        return ResponseDTO.<PreparationClassifyDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(preparationClassify)
                .message("Preparation classify retrieved successfully")
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<PreparationClassifyDTO> create(@Valid @RequestBody PreparationClassifyDTO preparationClassifyDTO) {
        PreparationClassifyDTO createdPreparationClassify = service.create(preparationClassifyDTO);

        return ResponseDTO.<PreparationClassifyDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdPreparationClassify)
                .message("Preparation classify created successfully")
                .build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<PreparationClassifyDTO> update(@PathVariable Long id, @Valid @RequestBody PreparationClassifyDTO preparationClassifyDTO) {
        PreparationClassifyDTO updatedPreparationClassify = service.update(preparationClassifyDTO, id);

        return ResponseDTO.<PreparationClassifyDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedPreparationClassify)
                .message("Preparation classify updated successfully")
                .build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<PreparationClassifyDTO> patch(@PathVariable Long id, @RequestBody PreparationClassifyDTO preparationClassifyDTO) {
        PreparationClassifyDTO patchedPreparationClassify = service.patch(preparationClassifyDTO, id);

        return ResponseDTO.<PreparationClassifyDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedPreparationClassify)
                .message("Preparation classify updated with patch successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        return ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Preparation classify deleted successfully" : "Failed to delete preparation classify")
                .build();
    }
}
