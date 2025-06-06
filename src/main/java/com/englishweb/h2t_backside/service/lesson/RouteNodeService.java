package com.englishweb.h2t_backside.service.lesson;

import com.englishweb.h2t_backside.dto.feature.RouteNodeDTO;
import com.englishweb.h2t_backside.model.enummodel.RouteNodeEnum;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface RouteNodeService extends BaseService<RouteNodeDTO> {

    List<RouteNodeDTO> findByRouteId(Long routeId);

    boolean verifyValidRouteNode(Long id);

    RouteNodeDTO findByNodeIdAndRouteNodeType(Long nodeId, RouteNodeEnum type);
}
