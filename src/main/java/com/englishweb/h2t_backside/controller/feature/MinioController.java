package com.englishweb.h2t_backside.controller.feature;

import com.englishweb.h2t_backside.dto.feature.FileUploadDTO;
import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.feature.MinioService;
import com.englishweb.h2t_backside.service.feature.impl.MinioServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/minio")
@RequiredArgsConstructor
@Slf4j
public class MinioController {

    private final MinioService service;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> uploadFile(
            @RequestBody FileUploadDTO request) throws IOException {
        String fileUrl = service.uploadFileFromBase64(
                request.getPath(),
                request.getBase64(),
                request.getFileName(),
                MinioServiceImpl.RandomName.valueOf(request.getRandomName())
        );

        return new ResponseDTO<>(
                ResponseStatusEnum.SUCCESS,
                fileUrl,
                "File uploaded successfully");
    }

    @GetMapping("/{objectName}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<byte[]> downloadFile(@PathVariable String objectName) {
        return new ResponseDTO<>(
                ResponseStatusEnum.SUCCESS,
                service.getFile(objectName),
                "File downloaded successfully");
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> deleteFile(@RequestParam String objectName) {
        service.deleteFile(objectName);
        return new ResponseDTO<>(
                ResponseStatusEnum.SUCCESS,
                null,
                "File deleted successfully");
    }
}
