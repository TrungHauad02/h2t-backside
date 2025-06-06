package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.filter.SubmitCompetitionFilterDTO;
import com.englishweb.h2t_backside.dto.test.SubmitCompetitionDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.SubmitCompetitionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/submit-competitions")
@AllArgsConstructor
public class SubmitCompetitionController {

    private final SubmitCompetitionService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<SubmitCompetitionDTO> findById(@PathVariable Long id) {
        SubmitCompetitionDTO competition = service.findById(id);
        return ResponseDTO.<SubmitCompetitionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(competition)
                .message("SubmitCompetition retrieved successfully")
                .build();
    }

    @GetMapping("/by-test-id-and-status")
    public ResponseDTO<List<SubmitCompetitionDTO>> findByTestIdAndStatus(
            @RequestParam Long testId,
            @RequestParam Boolean status
    ) {
        List<SubmitCompetitionDTO> results = service.findByTestIdAndStatus(testId, status);
        return ResponseDTO.<List<SubmitCompetitionDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(results)
                .message("Submissions fetched successfully")
                .build();
    }


    @PostMapping
    public ResponseEntity<ResponseDTO<SubmitCompetitionDTO>> create(@Valid @RequestBody SubmitCompetitionDTO dto) {
        SubmitCompetitionDTO createdCompetition = service.create(dto);

        ResponseDTO<SubmitCompetitionDTO> response = ResponseDTO.<SubmitCompetitionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdCompetition)
                .message("SubmitCompetition created successfully")
                .build();
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitCompetitionDTO>> update(@PathVariable Long id, @Valid @RequestBody SubmitCompetitionDTO dto) {
        SubmitCompetitionDTO updatedCompetition = service.update(dto, id);

        ResponseDTO<SubmitCompetitionDTO> response = ResponseDTO.<SubmitCompetitionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedCompetition)
                .message("SubmitCompetition updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitCompetitionDTO>> patch(@PathVariable Long id, @RequestBody SubmitCompetitionDTO dto) {
        SubmitCompetitionDTO patchedCompetition = service.patch(dto, id);

        ResponseDTO<SubmitCompetitionDTO> response = ResponseDTO.<SubmitCompetitionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedCompetition)
                .message("SubmitCompetition updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "SubmitCompetition deleted successfully" : "Failed to delete SubmitCompetition")
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/by-test-and-user")
    public ResponseDTO<SubmitCompetitionDTO> findByTestIdAndUserId(
            @RequestParam Long testId,
            @RequestParam Long userId,
             @RequestParam Boolean status
    ) {
        SubmitCompetitionDTO dto = service.findByTestIdAndUserIdAndStatus(testId, userId,status);
        return ResponseDTO.<SubmitCompetitionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("Found successfully")
                .build();
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<Page<SubmitCompetitionDTO>> searchWithFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String sortFields,
            @RequestParam(required = false) Long userId,
            @ModelAttribute SubmitCompetitionFilterDTO filter) {

        Page<SubmitCompetitionDTO> result = service.searchWithFilters(page, size, sortFields, filter, userId);

        return ResponseDTO.<Page<SubmitCompetitionDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("SubmitCompetition retrieved successfully with filters")
                .build();
    }

    @GetMapping("/leaderboard")
    public ResponseDTO<List<SubmitCompetitionDTO>> getLeaderboard() {
        List<SubmitCompetitionDTO> leaderboard = service.getLeaderboard();
        return ResponseDTO.<List<SubmitCompetitionDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(leaderboard)
                .message("Leaderboard retrieved successfully")
                .build();
    }
}
