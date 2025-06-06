package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.LessonQuestionDTO;
import com.englishweb.h2t_backside.dto.lesson.TopicDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.TopicService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/topics")
@AllArgsConstructor
public class TopicController {

    private final TopicService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<TopicDTO> findById(@PathVariable Long id) {
        TopicDTO topic = service.findById(id);

        return ResponseDTO.<TopicDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(topic)
                .message("Topic retrieved successfully")
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<TopicDTO> create(@Valid @RequestBody TopicDTO topicDTO) {
        TopicDTO createdTopic = service.create(topicDTO);

        return ResponseDTO.<TopicDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdTopic)
                .message("Topic created successfully")
                .build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<TopicDTO> update(@PathVariable Long id, @Valid @RequestBody TopicDTO topicDTO) {
        TopicDTO updatedTopic = service.update(topicDTO, id);

        return ResponseDTO.<TopicDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedTopic)
                .message("Topic updated successfully")
                .build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<TopicDTO> patch(@PathVariable Long id, @RequestBody TopicDTO topicDTO) {
        TopicDTO patchedTopic = service.patch(topicDTO, id);

        return ResponseDTO.<TopicDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedTopic)
                .message("Topic updated with patch successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        return ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Topic deleted successfully" : "Failed to delete topic")
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<Page<TopicDTO>> searchWithFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String sortFields,
            @ModelAttribute LessonFilterDTO filter) {

        Page<TopicDTO> topics = service.searchWithFilters(page, size, sortFields, filter);

        return ResponseDTO.<Page<TopicDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(topics)
                .message("Topics retrieved successfully with filters")
                .build();
    }

    @GetMapping("/questions")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<LessonQuestionDTO>> findQuestionByTopicId(
            @RequestParam Long lessonId,
            @RequestParam (required = false) Boolean status) {
        List<LessonQuestionDTO> questions = service.findQuestionByLessonId(lessonId, status);

        return ResponseDTO.<List<LessonQuestionDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(questions)
                .message("Questions retrieved successfully for the topic")
                .build();
    }

    @GetMapping("/{id}/verify")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> verify(@PathVariable Long id) {
        boolean result = service.verifyValidLesson(id);
        if (result) {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Topic lesson verified successfully")
                    .build();
        } else {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Topic lesson not valid")
                    .build();
        }
    }
}
