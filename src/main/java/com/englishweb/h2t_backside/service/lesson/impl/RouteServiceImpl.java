package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.feature.RouteDTO;
import com.englishweb.h2t_backside.dto.feature.RouteNodeDTO;
import com.englishweb.h2t_backside.dto.filter.RouteFilterDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.RouteMapper;
import com.englishweb.h2t_backside.model.features.Route;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.repository.RouteRepository;
import com.englishweb.h2t_backside.repository.specifications.RouteSpecification;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.lesson.RouteNodeService;
import com.englishweb.h2t_backside.service.lesson.RouteService;
import com.englishweb.h2t_backside.utils.BaseFilterSpecification;
import com.englishweb.h2t_backside.utils.ParseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RouteServiceImpl extends BaseServiceImpl<RouteDTO, Route, RouteRepository> implements RouteService {

    private final RouteNodeService routeNodeService;
    RouteMapper mapper;

    public RouteServiceImpl(RouteRepository repository, RouteMapper mapper, DiscordNotifier discordNotifier,@Lazy RouteNodeService routeNodeService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.routeNodeService = routeNodeService;
    }

    @Override
    public boolean delete(Long id) {
        // Delete other resources associated with the route
        RouteDTO dto = super.findById(id);
        for(RouteNodeDTO node : dto.getRouteNodes()) {
            // Call delete service to delete other resources
            routeNodeService.delete(node.getId());
        }
        return super.delete(id);
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("Route with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(RouteDTO dto, Exception ex) {
        log.error("Error creating route: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating route: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.ROUTE_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(RouteDTO dto, Long id, Exception ex) {
        log.error("Error updating route: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating route: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.ROUTE_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("Route with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(RouteDTO dto, Route entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected Route convertToEntity(RouteDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected RouteDTO convertToDTO(Route entity) {
        return mapper.convertToDTO(entity);
    }

    @Override
    public Page<RouteDTO> findByOwnerId(int page, int size, String sortFields, RouteFilterDTO filter, Long ownerId) {
        Pageable pageable = ParseData.parsePageArgs(page, size, sortFields, Route.class);

        Specification<Route> specification = BaseFilterSpecification.applyBaseFilters(filter);

        if (ownerId != null) {
            specification = specification.and(RouteSpecification.findByOwnerId(ownerId));
        }

        if (filter.getTitle() != null && !filter.getTitle().isEmpty()) {
            specification = specification.and(RouteSpecification.findByTitle(filter.getTitle()));
        }

        return repository.findAll(specification, pageable).map(this::convertToDTO);
    }

    @Override
    public List<RouteDTO> findByOwnerId(Long ownerId) {
        Specification<Route> specification = null;

        if (ownerId != null) {
            specification = RouteSpecification.findByOwnerId(ownerId);
        }

        return repository.findAll(specification).stream().map(this::convertToDTO).toList();
    }

    @Override
    public boolean verifyValidRoute(Long id) {
        RouteDTO route = super.findById(id);
        return route.getRouteNodes().stream().anyMatch((routeNodeDTO -> routeNodeService.verifyValidRouteNode(routeNodeDTO.getId())));
    }

    @Override
    public List<RouteDTO> findLongestRoutes() {
        List<Route> longestRoutes = repository.findTop3ByOrderByRouteNodeCountDesc();

        return longestRoutes.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
