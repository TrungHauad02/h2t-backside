package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.feature.RouteDTO;
import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.filter.RouteFilterDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.RouteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/routes")
@AllArgsConstructor
public class RouteController {

    private final RouteService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<RouteDTO> findById(@PathVariable Long id) {
        RouteDTO route = service.findById(id);

        return ResponseDTO.<RouteDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(route)
                .message("Route retrieved successfully")
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<RouteDTO> create(@Valid @RequestBody RouteDTO routeDTO) {
        RouteDTO createdRoute = service.create(routeDTO);

        return ResponseDTO.<RouteDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdRoute)
                .message("Route created successfully")
                .build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<RouteDTO> update(@PathVariable Long id, @Valid @RequestBody RouteDTO routeDTO) {
        RouteDTO updatedRoute = service.update(routeDTO, id);

        return ResponseDTO.<RouteDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedRoute)
                .message("Route updated successfully")
                .build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<RouteDTO> patch(@PathVariable Long id, @RequestBody RouteDTO routeDTO) {
        RouteDTO patchedRoute = service.patch(routeDTO, id);

        return ResponseDTO.<RouteDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedRoute)
                .message("Route updated with patch successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        return ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Route deleted successfully" : "Failed to delete route")
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<Page<RouteDTO>> findByOwnerId(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String sortFields,
            @ModelAttribute RouteFilterDTO filter,
            @RequestParam(required = false) Long ownerId) {
        Page<RouteDTO> result = service.findByOwnerId(page, size, sortFields, filter, ownerId);

        return ResponseDTO.<Page<RouteDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("Routes retrieved successfully")
                .build();
    }

    @GetMapping("/longest")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<RouteDTO>> findLongestRoutes() {
        List<RouteDTO> result = service.findLongestRoutes();
        return ResponseDTO.<List<RouteDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("Longest routes retrieved successfully")
                .build();
    }

    @GetMapping("/{id}/verify")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> verify(@PathVariable Long id) {
        boolean result = service.verifyValidRoute(id);
        if (result) {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Route verified successfully")
                    .build();
        } else {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Route not valid")
                    .build();
        }
    }
}
