package com.englishweb.h2t_backside.controller.feature;

import com.englishweb.h2t_backside.dto.feature.admindashboard.AdminDashboardDTO;
import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.feature.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final AdminDashboardService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<AdminDashboardDTO> getAdminDashboard() {
        return ResponseDTO.<AdminDashboardDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(service.getDashboardData())
                .message("Admin dashboard retrieved successfully")
                .build();
    }
}
