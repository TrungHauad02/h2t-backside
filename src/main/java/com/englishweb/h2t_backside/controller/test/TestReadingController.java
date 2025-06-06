    package com.englishweb.h2t_backside.controller.test;

    import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
    import com.englishweb.h2t_backside.dto.test.QuestionDTO;
    import com.englishweb.h2t_backside.dto.test.TestListeningDTO;
    import com.englishweb.h2t_backside.dto.test.TestReadingDTO;
    import com.englishweb.h2t_backside.dto.response.ResponseDTO;
    import com.englishweb.h2t_backside.service.test.TestReadingService;
    import jakarta.validation.Valid;
    import lombok.AllArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @Slf4j
    @RestController
    @RequestMapping("/api/test-readings")
    @AllArgsConstructor
    public class TestReadingController {

        private final TestReadingService service;

        @GetMapping("/{id}")
        @ResponseStatus(HttpStatus.OK)
        public ResponseDTO<TestReadingDTO> findById(@PathVariable Long id) {
            TestReadingDTO dto = service.findById(id);
            return ResponseDTO.<TestReadingDTO>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .data(dto)
                    .message("TestReading retrieved successfully")
                    .build();
        }
        @PostMapping("/by-ids")
        @ResponseStatus(HttpStatus.OK)
        public ResponseDTO<List<TestReadingDTO>> getByIds(
                @RequestBody List<Long> ids,
                @RequestParam(required = false) Boolean status) {

            List<TestReadingDTO> result = service.findByIdsAndStatus(ids, status);
            return ResponseDTO.<List<TestReadingDTO>>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .data(result)
                    .message("TestListening retrieved successfully")
                    .build();
        }
        @GetMapping("/questions")
        @ResponseStatus(HttpStatus.OK)
        public ResponseDTO<List<QuestionDTO>> findQuestionByTestId(
                @RequestParam Long testId,
                @RequestParam(required = false) Boolean status) {

            List<QuestionDTO> questions = service.findQuestionByTestId(testId, status);

            return ResponseDTO.<List<QuestionDTO>>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .data(questions)
                    .message("Questions retrieved successfully for the listening")
                    .build();
        }


        @PostMapping
        public ResponseEntity<ResponseDTO<TestReadingDTO>> create(@Valid @RequestBody TestReadingDTO dto) {
            TestReadingDTO createdTestReading = service.create(dto);

            ResponseDTO<TestReadingDTO> response = ResponseDTO.<TestReadingDTO>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .data(createdTestReading)
                    .message("TestReading created successfully")
                    .build();
            return ResponseEntity.ok(response);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ResponseDTO<TestReadingDTO>> update(@PathVariable Long id, @Valid @RequestBody TestReadingDTO dto) {
            TestReadingDTO updatedTestReading = service.update(dto, id);

            ResponseDTO<TestReadingDTO> response = ResponseDTO.<TestReadingDTO>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .data(updatedTestReading)
                    .message("TestReading updated successfully")
                    .build();
            return ResponseEntity.ok(response);
        }

        @PatchMapping("/{id}")
        public ResponseEntity<ResponseDTO<TestReadingDTO>> patch(@PathVariable Long id, @RequestBody TestReadingDTO dto) {
            TestReadingDTO patchedTestReading = service.patch(dto, id);

            ResponseDTO<TestReadingDTO> response = ResponseDTO.<TestReadingDTO>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .data(patchedTestReading)
                    .message("TestReading updated with patch successfully")
                    .build();
            return ResponseEntity.ok(response);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
            boolean result = service.delete(id);

            ResponseDTO<String> response = ResponseDTO.<String>builder()
                    .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                    .message(result ? "TestReading deleted successfully" : "Failed to delete test reading")
                    .build();
            return ResponseEntity.ok(response);
        }
        @GetMapping("/{id}/verify")
        @ResponseStatus(HttpStatus.OK)
        public ResponseDTO<String> verify(@PathVariable Long id) {
            boolean result = service.verifyValidTestReading(id);
            if (result) {
                return ResponseDTO.<String>builder()
                        .status(ResponseStatusEnum.SUCCESS)
                        .message("Test reading successfully")
                        .build();
            } else {
                return ResponseDTO.<String>builder()
                        .status(ResponseStatusEnum.FAIL)
                        .message("Test reading not valid")
                        .build();
            }
        }
    }
