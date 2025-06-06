package com.englishweb.h2t_backside.exception;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.response.ErrorDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
@AllArgsConstructor
@Hidden
public class GlobalExceptionHandler {

    private final DiscordNotifier discordNotifier;

    // Loi du lieu DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        String errorMessage = "Validation failed for fields: " + errors.toString();

        log.error("MethodArgumentNotValidException: Validation errors - {}", errors, ex);
        ErrorDTO errorDTO = ErrorDTO.builder()
                .message("Validation Failed")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(errorMessage)
                .instance(request.getMethod() + " " + request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(ErrorApiCodeContent.ARGUMENT_DTO_INVALID)
                .data(ex.getBindingResult().getTarget())
                .severity(SeverityEnum.LOW)
                .build();

        // Gửi thông báo lỗi đến Discord
        discordNotifier.buildErrorAndSend(errorDTO);
        return new ResponseDTO<>(ResponseStatusEnum.FAIL, errors, "Value of fields in dto does not valid");
    }

    // Loi khong tim thay
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseDTO<String> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        log.warn("Resource not found: {}", ex.getMessage());
        String errorMessage = "Resource Not Found";

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessage)
                .status(HttpStatus.NOT_FOUND.value())
                .detail(ex.getMessage())
                .instance(request.getMethod() + " " + request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(ErrorApiCodeContent.RESOURCE_NOT_FOUND)
                .data(ex.getResourceId())
                .severity(ex.getSeverityEnum())
                .build();
        discordNotifier.buildErrorAndSend(errorDTO);

        return ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.FAIL)
                .data(ex.getMessage())
                .message(errorMessage)
                .build();
    }

    // Lỗi xác thực thất bại
    @ExceptionHandler(AuthenticateException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseDTO<String> handleAuthenticateException(AuthenticateException ex, HttpServletRequest request) {
        log.warn("Authentication failed: {}", ex.getMessage());
        String errorMessage = "Authentication Failed";

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessage)
                .status(HttpStatus.UNAUTHORIZED.value())
                .detail(ex.getMessage())
                .instance(request.getMethod() + " " + request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(ErrorApiCodeContent.AUTHENTICATION_FAILED)
                .data(ex.getData())
                .severity(ex.getSeverityEnum())
                .build();
        discordNotifier.buildErrorAndSend(errorDTO);

        return ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.FAIL)
                .data(ex.getMessage())
                .message(errorMessage)
                .build();
    }

    // Loi khoi tao tai nguyen
    @ExceptionHandler(CreateResourceException.class)
    public ResponseEntity<ResponseDTO<String>> handleCreateResourceException(CreateResourceException ex, HttpServletRequest request) {
        return handleResourceException(ex.getStatus(), ex.getMessage(), ex.getErrorCode(), ex.getData(), ex.getSeverityEnum(), ex.getSeverityEnum(), request, "Failed to Create Resource");
    }

    // Loi cap nhat tai nguyen
    @ExceptionHandler(UpdateResourceException.class)
    public ResponseEntity<ResponseDTO<String>> handleUpdateResourceException(UpdateResourceException ex, HttpServletRequest request) {
        return handleResourceException(ex.getStatus(), ex.getMessage(), ex.getErrorCode(), ex.getData(), ex.getSeverityEnum(), ex.getSeverityEnum(), request, "Failed to Update Resource");
    }


    // Loi tham so khong hop le
    @ExceptionHandler(InvalidArgumentException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO<String> handleInvalidArgumentException(InvalidArgumentException ex, HttpServletRequest request) {
        log.warn("Invalid argument: {}", ex.getMessage());
        String errorMessage = "Invalid Argument";

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessage)
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(ex.getMessage())
                .instance(request.getMethod() + " " + request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(ex.getErrorCode())
                .data(ex.getData())
                .severity(ex.getSeverityEnum())
                .build();

        // Gửi thông báo lỗi đến Discord
        discordNotifier.buildErrorAndSend(errorDTO);
        return ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.FAIL)
                .message(errorMessage)
                .data(ex.getMessage())
                .build();
    }

    private ResponseEntity<ResponseDTO<String>> handleResourceException(HttpStatus status, String message, String errorCode, Object data, SeverityEnum severityEnum, SeverityEnum anEnum, HttpServletRequest request, String errorMessage) {
        log.warn("{}: {}", errorMessage, message);

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessage)
                .status(status.value())
                .detail(message)
                .instance(request.getMethod() + " " + request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(errorCode)
                .data(data)
                .severity(severityEnum)
                .build();

        // Gửi thông báo lỗi đến Discord
        discordNotifier.buildErrorAndSend(errorDTO);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.FAIL)
                .message(errorMessage)
                .data(message)
                .build();

        return new ResponseEntity<>(response, status);
    }

    // Xử lý lỗi sai dữ liệu đầu vào kiểu enum
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO<String> handleEnumTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        log.warn("Method argument type mismatch: {}", ex.getMessage());
        String errorMessage = "Method argument type mismatch";

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessage)
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(ex.getMessage())
                .instance(request.getMethod() + " " + request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(ErrorApiCodeContent.ARGUMENT_TYPE_MISMATCH)
                .data(ex.getName())
                .severity(SeverityEnum.LOW)
                .build();
        discordNotifier.buildErrorAndSend(errorDTO);

        return ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.FAIL)
                .message(errorMessage)
                .data(ex.getMessage())
                .build();
    }

    // Xử lý lỗi sai dữ liệu đầu vào
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.warn("HttpMessageNotReadableException: {}", ex.getMessage());
        String errorMessage = "Invalid input data format";

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessage)
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(ex.getMessage())
                .instance(request.getMethod() + " " + request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(ErrorApiCodeContent.ARGUMENT_TYPE_MISMATCH)
                .data(ex.getMessage())
                .severity(SeverityEnum.LOW)
                .build();
        discordNotifier.buildErrorAndSend(errorDTO);

        return ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.FAIL)
                .message(errorMessage)
                .data(ex.getMessage())
                .build();
    }

    // Xử lý lỗi thieu tham so
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO<String> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpServletRequest request) {
        log.warn("Missing Servlet Request Parameter Exception: {}", ex.getMessage());
        String errorMessage = "Missing Request Parameter";

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessage)
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(ex.getMessage())
                .instance(request.getMethod() + " " + request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(ErrorApiCodeContent.MISSING_REQUEST_PARAMETER)
                .data(ex.getParameterName())
                .severity(SeverityEnum.LOW)
                .build();
        discordNotifier.buildErrorAndSend(errorDTO);

        return ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.FAIL)
                .message(errorMessage)
                .data(ex.getMessage())
                .build();
    }

    @ExceptionHandler(IOException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseDTO<String>> handleIOException(IOException ex, HttpServletRequest request) {
        log.error("IOException: {}", ex.getMessage());

        String errorMessage = "IO Exception: Unexpected error when reading/writing file or parser data, check logs in discord server for more information";
        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessage)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail(ex.getMessage())
                .instance(request.getMethod() + " " + request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(ErrorApiCodeContent.IO_EXCEPTION)
                .data(ex.getMessage())
                .severity(SeverityEnum.HIGH)
                .build();

        discordNotifier.buildErrorAndSend(errorDTO);
        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.FAIL)
                .message(errorMessage)
                .data(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnauthorizedOpenRouterException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDTO<String> handleUnauthorizedOpenRouterException(UnauthorizedOpenRouterException ex, HttpServletRequest request) {
        log.error("UnauthorizedOpenRouterException: {}", ex.getMessage());
        String errorMessage = "Unauthorized OpenRouter Exception. Please check apiKey for openRouter";
        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessage)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail(ex.getMessage())
                .instance(request.getMethod() + " " + request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(ErrorApiCodeContent.UNAUTHORIZED)
                .data(ex.getMessage())
                .severity(ex.getSeverityEnum())
                .build();
        discordNotifier.buildErrorAndSend(errorDTO);
        return ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.FAIL)
                .message(errorMessage)
                .data(ex.getMessage())
                .build();
    }

    @ExceptionHandler(OpenRouterException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDTO<String> handleOpenRouterException(OpenRouterException ex, HttpServletRequest request) {
        log.error("OpenRouterException: {}", ex.getMessage());
        String errorMessage = "OpenRouter Exception: Unexpected error, check logs for more information";
        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessage)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail(ex.getMessage())
                .instance(request.getMethod() + " " + request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(ErrorApiCodeContent.OPEN_ROUTER_EXCEPTION)
                .data(ex.getMessage())
                .severity(ex.getSeverityEnum())
                .build();
        discordNotifier.buildErrorAndSend(errorDTO);
        return ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.FAIL)
                .message(errorMessage)
                .data(ex.getMessage())
                .build();
    }

    @ExceptionHandler(JsonProcessingException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDTO<String> handleJsonProcessingException(JsonProcessingException ex, HttpServletRequest request) {
        log.error("JsonProcessingException: {}", ex.getMessage());
        String errorMessage = "JsonProcessing Exception: Unexpected parse data, check logs for more information";
        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessage)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail(ex.getMessage())
                .instance(request.getMethod() + " " + request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(ErrorApiCodeContent.JSON_PROCESSING_EXCEPTION)
                .data(ex.getMessage())
                .severity(SeverityEnum.MEDIUM)
                .build();
        discordNotifier.buildErrorAndSend(errorDTO);
        return ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.FAIL)
                .message(errorMessage)
                .data(ex.getMessage())
                .build();
    }

    @ExceptionHandler(SpeechProcessingException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDTO<String> handleSpeechProcessingException(SpeechProcessingException ex, HttpServletRequest request) {
        log.error("SpeechProcessingException: {}", ex.getMessage());
        String errorMessage = "SpeechProcessing Exception: Speech Evaluation API maybe down, check logs for more information";
        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessage)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail(ex.getMessage())
                .instance(request.getMethod() + " " + request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(ErrorApiCodeContent.SPEECH_PROCESSING_EXCEPTION)
                .data(ex.getMessage())
                .severity(ex.getSeverityEnum())
                .build();
        discordNotifier.buildErrorAndSend(errorDTO);
        return ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.FAIL)
                .message(errorMessage)
                .data(ex.getMessage())
                .build();
    }

    @ExceptionHandler(TextToSpeechException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDTO<String> handleTextToSpeechException(TextToSpeechException ex, HttpServletRequest request) {
        log.error("TextToSpeechException: {}", ex.getMessage());
        String errorMessage = "TextToSpeech Exception: Text to Speech API maybe down or something when wrong, check logs for more information";
        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessage)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail(ex.getMessage())
                .instance(request.getMethod() + " " + request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(ErrorApiCodeContent.TEXT_TO_SPEECH_EXCEPTION)
                .data(ex.getMessage())
                .severity(ex.getSeverityEnum())
                .build();
        discordNotifier.buildErrorAndSend(errorDTO);
        return ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.FAIL)
                .message(errorMessage)
                .data(ex.getMessage())
                .build();
    }

    @ExceptionHandler(CommentTestException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDTO<String> handleCommentTestException(CommentTestException ex, HttpServletRequest request) {
        log.error("CommentTestException: {}", ex.getMessage());
        String errorMessage = "Comment Test Exception: Error when try to generate comment for test, " +
                "check logs for more information";
        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessage)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail(ex.getMessage())
                .instance(request.getMethod() + " " + request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(ErrorApiCodeContent.COMMENT_TEST_EXCEPTION)
                .data(ex.getMessage())
                .severity(ex.getSeverityEnum())
                .build();
        discordNotifier.buildErrorAndSend(errorDTO);
        return ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.FAIL)
                .message(errorMessage)
                .data(ex.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDTO<String> handleException(Exception ex, HttpServletRequest request) {
        log.error("Exception: {}", ex.getMessage());
        String errorMessage = "Unexpected Exception: Unexpected error, check logs in discord server for more information";
        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessage)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail(ex.getMessage())
                .instance(request.getMethod() + " " + request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(ErrorApiCodeContent.UNEXPECTED_ERROR)
                .data(ex.getMessage())
                .severity(SeverityEnum.HIGH)
                .build();
        discordNotifier.buildErrorAndSend(errorDTO);
        return ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.FAIL)
                .message(errorMessage)
                .data(ex.getMessage())
                .build();
    }
}
