package com.englishweb.h2t_backside.controller.auth;

import com.englishweb.h2t_backside.dto.filter.UserFilterDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.feature.UserDTO;
import com.englishweb.h2t_backside.service.feature.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<UserDTO> findById(@PathVariable Long id) {
        UserDTO user = service.findById(id);
        if (user == null) {
            log.warn("User with ID {} not found", id);
            return ResponseDTO.<UserDTO>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("User not found")
                    .build();
        }

        return ResponseDTO.<UserDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(user)
                .message("User retrieved successfully")
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<UserDTO> create(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = service.create(userDTO);
        if (createdUser == null) {
            return ResponseDTO.<UserDTO>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Failed to create user")
                    .build();
        }

        return ResponseDTO.<UserDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdUser)
                .message("User created successfully")
                .build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = service.update(userDTO, id);

        return ResponseDTO.<UserDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedUser)
                .message("User updated successfully")
                .build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<UserDTO> patch(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO patchedUser = service.patch(userDTO, id);

        return ResponseDTO.<UserDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedUser)
                .message("User updated with patch successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        return ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "User deleted successfully" : "Failed to delete user")
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<Page<UserDTO>> searchWithFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String sortFields,
            @ModelAttribute UserFilterDTO filter) {

        Page<UserDTO> users = service.searchWithFilters(
                page, size, sortFields, filter);

       return ResponseDTO.<Page<UserDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(users)
                .message("Users retrieved successfully with filters")
                .build();
    }

    @PostMapping("/by-ids-and-status")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<UserDTO>> findByIdsAndStatus(
            @RequestParam List<Long> ids,
            @RequestParam Boolean status) {
        List<UserDTO> users = service.findByIdInAndStatus(ids, status);

        return ResponseDTO.<List<UserDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(users)
                .message("Users retrieved successfully")
                .build();
    }

    @GetMapping("/{userId}/complete-route-node/{routeNodeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> completeRouteNode(@PathVariable Long userId, @PathVariable Long routeNodeId) {
        service.completeRouteNode(userId, routeNodeId);
        return ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .message("Route node completed successfully")
                .build();
    }

    @GetMapping("/{userId}/process-by-route-id/{routeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<Long>> getProcessByRouteId(@PathVariable Long userId, @PathVariable Long routeId) {
        List<Long> process = service.getProcessByRouteId(userId, routeId);
        return ResponseDTO.<List<Long>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(process)
                .message("Process retrieved successfully")
                .build();
    }
}

