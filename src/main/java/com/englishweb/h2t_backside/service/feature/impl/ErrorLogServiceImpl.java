package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.feature.ErrorLogDTO;
import com.englishweb.h2t_backside.dto.filter.ErrorLogFilterDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.mapper.ErrorLogMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.log.ErrorLog;
import com.englishweb.h2t_backside.repository.ErrorLogRepository;
import com.englishweb.h2t_backside.service.feature.ErrorLogService;
import com.englishweb.h2t_backside.utils.ErrorLogPagination;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ErrorLogServiceImpl implements ErrorLogService {
    ErrorLogRepository repository;
    private final ErrorLogMapper mapper;

    @Override
    public ErrorLogDTO findById(Long id) {
        log.info("Finding error log by ID: {}", id);
        ErrorLog entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, "ErrorLog not found", SeverityEnum.MEDIUM));
        log.info("Found error log with ID: {}", id);
        return convertToDTO(entity);
    }

    @Override
    public ErrorLogDTO create(ErrorLogDTO dto) {
        try {
            log.info("Creating error log");
            ErrorLog entity = convertToEntity(dto);
            entity.setId(null);
            ErrorLog created = repository.save(entity);
            log.info("Created error log successfully");
            return convertToDTO(created);
        } catch (Exception e) {
            log.error("Error when creating error log: {}", e.getMessage());
            throw new CreateResourceException(dto, e.getMessage(), ErrorApiCodeContent.ERROR_LOG_CREATED_FAIL, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
        }
    }

    @Override
    public ErrorLogDTO update(Long id, ErrorLogDTO dto) {
        log.info("Updating error log entity with PATCH-style update");

        try {
            ErrorLog existingEntity = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(id, "ErrorLog not found", SeverityEnum.MEDIUM));

            patchEntityFromDTO(dto, existingEntity);

            ErrorLog savedEntity = repository.save(existingEntity);
            ErrorLogDTO savedDTO = convertToDTO(savedEntity);
            log.info("Updated error log with ID: {}", savedDTO.getId());

            return savedDTO;
        } catch (Exception e) {
            log.error("Error updating error log with ID {}: {}", id, e.getMessage());
            throw new CreateResourceException(dto, e.getMessage(), ErrorApiCodeContent.ERROR_LOG_UPDATED_FAIL, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
        }
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting error log with ID: {}", id);
        repository.deleteById(id);
        log.info("Deleted error log with ID: {}", id);
    }

    @Override
    public void deleteAllByStatusFalseAndSeverityIn(List<SeverityEnum> severityList) {
        log.info("Deleting error logs with status=false and severity in: {}", severityList);
        repository.deleteAllByStatusFalseAndSeverityIn(severityList);
        log.info("Deleted error logs with status=false and severity in: {}", severityList);
    }

    @Override
    public Page<ErrorLogDTO> findByPage(int page, int size, String sortFields, ErrorLogFilterDTO filter) {
        Page<ErrorLog> errorLogs = ErrorLogPagination.searchWithFiltersGeneric(
                page, size, sortFields, filter, repository, ErrorLog.class
        );
        return errorLogs.map(this::convertToDTO);
    }

    protected void patchEntityFromDTO(ErrorLogDTO dto, ErrorLog entity) { mapper.patchEntityFromDTO(dto, entity); }

    protected ErrorLog convertToEntity(ErrorLogDTO dto) { return mapper.convertToEntity(dto); }

    protected ErrorLogDTO convertToDTO(ErrorLog entity) { return mapper.convertToDTO(entity); }

}
