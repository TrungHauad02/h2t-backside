package com.englishweb.h2t_backside.controller.feature;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.dto.feature.teacherdashboard.TeacherDashboardDTO;
import com.englishweb.h2t_backside.service.feature.TeacherDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher-dashboard")
@RequiredArgsConstructor
public class TeacherDashboardController {

    private final TeacherDashboardService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<TeacherDashboardDTO> getTeacherDashboard(@RequestParam Long teacherId){
        TeacherDashboardDTO data = service.getTeacherDashboardByTeacherId(teacherId);

        return ResponseDTO.<TeacherDashboardDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(data)
                .message("Retrieve teacher dashboard data")
                .build();
    }
}
