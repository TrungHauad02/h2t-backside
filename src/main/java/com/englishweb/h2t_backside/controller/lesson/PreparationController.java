package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.lesson.PreparationDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.PreparationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/preparations")
@AllArgsConstructor
public class PreparationController {

    private final PreparationService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<PreparationDTO> findById(@PathVariable Long id) {
        PreparationDTO preparation = service.findById(id);

        return ResponseDTO.<PreparationDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(preparation)
                .message("Preparation retrieved successfully")
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<PreparationDTO> create(@Valid @RequestBody PreparationDTO preparationDTO) {
        PreparationDTO createdPreparation = service.create(preparationDTO);

        return ResponseDTO.<PreparationDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdPreparation)
                .message("Preparation created successfully")
                .build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<PreparationDTO> update(@PathVariable Long id, @Valid @RequestBody PreparationDTO preparationDTO) {
        PreparationDTO updatedPreparation = service.update(preparationDTO, id);

        return ResponseDTO.<PreparationDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedPreparation)
                .message("Preparation updated successfully")
                .build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<PreparationDTO> patch(@PathVariable Long id, @RequestBody PreparationDTO preparationDTO) {
        PreparationDTO patchedPreparation = service.patch(preparationDTO, id);

        return ResponseDTO.<PreparationDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedPreparation)
                .message("Preparation updated with patch successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        return ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Preparation deleted successfully" : "Failed to delete preparation")
                .build();
    }

    @GetMapping("/{id}/verify")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> verify(@PathVariable Long id) {
        boolean result = service.verifyValidPreparation(id);
        if (result) {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Preparation verified successfully")
                    .build();
        } else {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Preparation not valid")
                    .build();
        }
    }
}
