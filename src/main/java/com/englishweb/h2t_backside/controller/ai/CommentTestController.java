package com.englishweb.h2t_backside.controller.ai;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.ai.TestCommentRequestDTO;
import com.englishweb.h2t_backside.dto.ai.TestCommentResponseDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.ai.CommentTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment-test")
@RequiredArgsConstructor
@Slf4j
public class CommentTestController {

    private final CommentTestService service;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<TestCommentResponseDTO> commentTest(@RequestBody TestCommentRequestDTO request) {
        return ResponseDTO.<TestCommentResponseDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(service.comment(request))
                .message("Test comment generated successfully")
                .build();
    }
}
