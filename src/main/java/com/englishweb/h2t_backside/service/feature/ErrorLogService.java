package com.englishweb.h2t_backside.service.feature;

import com.englishweb.h2t_backside.dto.feature.ErrorLogDTO;
import com.englishweb.h2t_backside.dto.filter.ErrorLogFilterDTO;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ErrorLogService {
    ErrorLogDTO findById(Long id);

    ErrorLogDTO create(ErrorLogDTO dto);

    ErrorLogDTO update(Long id, ErrorLogDTO dto);

    void delete(Long id);

    void deleteAllByStatusFalseAndSeverityIn(List<SeverityEnum> severityList);

    Page<ErrorLogDTO> findByPage(int page, int size, String sortFields, ErrorLogFilterDTO filter);
}
