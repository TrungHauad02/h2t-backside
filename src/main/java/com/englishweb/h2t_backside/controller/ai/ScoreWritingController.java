package com.englishweb.h2t_backside.controller.ai;


import com.englishweb.h2t_backside.dto.ai.ScoreWritingRequestDTO;
import com.englishweb.h2t_backside.dto.ai.WritingScoreDTO;
import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.ai.ScoreWritingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/score-writing")
@RequiredArgsConstructor
@Slf4j
public class ScoreWritingController {

    private final ScoreWritingService service;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<WritingScoreDTO> scoreWriting(@RequestBody ScoreWritingRequestDTO request) {
        return ResponseDTO.<WritingScoreDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(service.scoreWriting(request.getText(), request.getTopic()))
                .message("Writing scoring completed successfully")
                .build();
    }
}
