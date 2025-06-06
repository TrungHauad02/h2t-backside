package com.englishweb.h2t_backside.controller.feature;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.feature.teacheradvancedashboard.TeacherAdvanceDashboardDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.feature.TeacherAdvanceDashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/teacher-advance/dashboard")
@RequiredArgsConstructor
public class TeacherAdvanceDashboardController {

    private final TeacherAdvanceDashboardService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<TeacherAdvanceDashboardDTO> getTeacherAdvanceDashboard(@RequestParam(required = true) Long teacherId) {
        TeacherAdvanceDashboardDTO data = service.getTeacherAdvanceDashboardByTeacherId(teacherId);

        return ResponseDTO.<TeacherAdvanceDashboardDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(data)
                .message("Teacher advance dashboard retrieved successfully")
                .build();
    }
}
