package com.englishweb.h2t_backside.service.feature;

import java.util.List;

public interface BaseService<DTO> {

    DTO findById(Long id);

    DTO create(DTO dto);

    DTO update(DTO dto, Long id);

    DTO patch(DTO dto, Long id);

    boolean delete(Long id);

    boolean isExist(Long id);

    void deleteAll(List<Long> ids);
}
