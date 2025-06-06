package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.feature.RouteNodeDTO;
import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.model.enummodel.RouteNodeEnum;
import com.englishweb.h2t_backside.service.lesson.RouteNodeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/routeNodes")
@AllArgsConstructor
public class RouteNodeController {

    private final RouteNodeService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<RouteNodeDTO> findById(@PathVariable Long id) {
        RouteNodeDTO routeNode = service.findById(id);

        return ResponseDTO.<RouteNodeDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(routeNode)
                .message("Route node retrieved successfully")
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<RouteNodeDTO> create(@Valid @RequestBody RouteNodeDTO routeNodeDTO) {
        RouteNodeDTO createdRouteNode = service.create(routeNodeDTO);

        return ResponseDTO.<RouteNodeDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdRouteNode)
                .message("Route node created successfully")
                .build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<RouteNodeDTO> update(@PathVariable Long id, @Valid @RequestBody RouteNodeDTO routeNodeDTO) {
        RouteNodeDTO updatedRouteNode = service.update(routeNodeDTO, id);

        return ResponseDTO.<RouteNodeDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedRouteNode)
                .message("Route node updated successfully")
                .build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<RouteNodeDTO> patch(@PathVariable Long id, @RequestBody RouteNodeDTO routeNodeDTO) {
        RouteNodeDTO patchedRouteNode = service.patch(routeNodeDTO, id);

        return ResponseDTO.<RouteNodeDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedRouteNode)
                .message("Route node updated with patch successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        return ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Route node deleted successfully" : "Failed to delete route node")
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<RouteNodeDTO> findByNodeIdAndRouteNodeType(@RequestParam Long nodeId, @RequestParam RouteNodeEnum type) {
        RouteNodeDTO routeNode = service.findByNodeIdAndRouteNodeType(nodeId, type);
        return ResponseDTO.<RouteNodeDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(routeNode)
                .message("Route node retrieved successfully")
                .build();
    }
}
