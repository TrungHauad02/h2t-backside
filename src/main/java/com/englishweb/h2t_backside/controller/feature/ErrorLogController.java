package com.englishweb.h2t_backside.controller.feature;

import com.englishweb.h2t_backside.dto.feature.ErrorLogDTO;
import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.filter.ErrorLogFilterDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.service.feature.ErrorLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/error-logs")
@RequiredArgsConstructor
@Slf4j
public class ErrorLogController {

    private final ErrorLogService errorLogService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<ErrorLogDTO> findById(@PathVariable Long id) {
        ErrorLogDTO errorLog = errorLogService.findById(id);
        return ResponseDTO.<ErrorLogDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(errorLog)
                .message("Error log retrieved successfully")
                .build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<ErrorLogDTO> update(@PathVariable Long id, @Valid @RequestBody ErrorLogDTO errorLogDTO) {
        ErrorLogDTO updatedErrorLog = errorLogService.update(id, errorLogDTO);

        return ResponseDTO.<ErrorLogDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedErrorLog)
                .message("Error log updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<ErrorLogDTO> delete(@PathVariable Long id) {
        errorLogService.delete(id);
        return ResponseDTO.<ErrorLogDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .message("Error log deleted successfully")
                .build();
    }

    @DeleteMapping("/bulk")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<Void> bulkDelete(@RequestBody Map<String, List<SeverityEnum>> request) {
        List<SeverityEnum> severityList = request.get("severityList");
        errorLogService.deleteAllByStatusFalseAndSeverityIn(severityList);

        return ResponseDTO.<Void>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .message("Error logs deleted successfully")
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<Page<ErrorLogDTO>> searchWithFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String sortFields,
            @ModelAttribute ErrorLogFilterDTO filter) {

        Page<ErrorLogDTO> errorLogs = errorLogService.findByPage(page, size, sortFields, filter);

        return ResponseDTO.<Page<ErrorLogDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(errorLogs)
                .message("Error logs retrieved successfully with filters")
                .build();
    }

}

