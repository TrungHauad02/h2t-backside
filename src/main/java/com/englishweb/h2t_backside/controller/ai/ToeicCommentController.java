package com.englishweb.h2t_backside.controller.ai;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.ai.ToeicCommentRequestDTO;
import com.englishweb.h2t_backside.dto.ai.TestCommentResponseDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.ai.ToeicCommentTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/toeic-comment")
@RequiredArgsConstructor
@Slf4j
public class ToeicCommentController {

    private final ToeicCommentTestService service;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<TestCommentResponseDTO> commentToeicTest(@RequestBody ToeicCommentRequestDTO request) {
        return ResponseDTO.<TestCommentResponseDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(service.comment(request))
                .message("TOEIC test comment generated successfully")
                .build();
    }
}