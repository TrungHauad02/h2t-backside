package com.englishweb.h2t_backside.controller.ai;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.ai.LLMService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/llm")
@RequiredArgsConstructor
@Slf4j
public class LLMController {

    private final LLMService service;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> generateText(@RequestBody String prompt) {
        String result = service.generateText(prompt);
        return ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("Text generated successfully")
                .build();
    }
}
