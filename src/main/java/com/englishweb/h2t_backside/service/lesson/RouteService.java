package com.englishweb.h2t_backside.service.lesson;

import com.englishweb.h2t_backside.dto.feature.RouteDTO;
import com.englishweb.h2t_backside.dto.filter.RouteFilterDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RouteService extends BaseService<RouteDTO> {

    Page<RouteDTO> findByOwnerId(int page, int size, String sortFields, RouteFilterDTO filter, Long ownerId);

    List<RouteDTO> findByOwnerId(Long ownerId);

    List<RouteDTO> findLongestRoutes();

    boolean verifyValidRoute(Long id);
}
