package com.englishweb.h2t_backside.service.feature;

import com.englishweb.h2t_backside.dto.feature.UserDTO;
import com.englishweb.h2t_backside.dto.filter.UserFilterDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService extends BaseService<UserDTO>{

    Page<UserDTO> searchWithFilters(int page, int size, String sortFields, UserFilterDTO filter);

    List<UserDTO> findByIdInAndStatus(List<Long> ids, Boolean status);

    void completeRouteNode(Long userId, Long routeNodeId);

    List<Long> getProcessByRouteId(Long userId, Long routeId);
}
