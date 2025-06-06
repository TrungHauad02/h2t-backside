package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.filter.CompetitionTestFilterDTO;
import com.englishweb.h2t_backside.dto.test.CompetitionTestDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.CompetitionTestService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/competition-tests")
@AllArgsConstructor
public class CompetitionTestController {
    private final CompetitionTestService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<CompetitionTestDTO> findById(@PathVariable Long id) {
        CompetitionTestDTO dto = service.findById(id);
        return ResponseDTO.<CompetitionTestDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("CompetitionTest retrieved successfully")
                .build();
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<CompetitionTestDTO>> create(@Valid @RequestBody CompetitionTestDTO dto) {
        CompetitionTestDTO createdTest = service.create(dto);

        ResponseDTO<CompetitionTestDTO> response = ResponseDTO.<CompetitionTestDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdTest)
                .message("CompetitionTest created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<CompetitionTestDTO>> update(@PathVariable Long id, @Valid @RequestBody CompetitionTestDTO dto) {
        CompetitionTestDTO updatedTest = service.update(dto, id);

        ResponseDTO<CompetitionTestDTO> response = ResponseDTO.<CompetitionTestDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedTest)
                .message("CompetitionTest updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<CompetitionTestDTO>> patch(@PathVariable Long id, @RequestBody CompetitionTestDTO dto) {
        CompetitionTestDTO patchedTest = service.patch(dto, id);

        ResponseDTO<CompetitionTestDTO> response = ResponseDTO.<CompetitionTestDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedTest)
                .message("CompetitionTest updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "CompetitionTest deleted successfully" : "Failed to delete CompetitionTest")
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<Page<CompetitionTestDTO>> searchCompetitionWithFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String sortFields,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long ownerId,
            @ModelAttribute CompetitionTestFilterDTO filter) {

        Page<CompetitionTestDTO> results = service.searchWithFilters(page, size, sortFields, filter, userId,ownerId);

        return ResponseDTO.<Page<CompetitionTestDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(results)
                .message("Competition results retrieved successfully with filters")
                .build();
    }
    @GetMapping("/{id}/verify")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> verify(@PathVariable Long id) {
        boolean result = service.verifyValidCompetitionTest(id);
        if (result) {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Competition test verified successfully")
                    .build();
        } else {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Competition test not valid")
                    .build();
        }
    }

    @GetMapping("/recent-completed")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<CompetitionTestDTO> getLastCompletedCompetition() {
        CompetitionTestDTO result = service.getLastCompletedCompetition();
        return ResponseDTO.<CompetitionTestDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("CompetitionTest retrieved successfully")
                .build();
    }
}
