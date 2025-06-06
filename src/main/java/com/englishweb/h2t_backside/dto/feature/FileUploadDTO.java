package com.englishweb.h2t_backside.dto.feature;

import lombok.Data;

@Data
public class FileUploadDTO {
    private String base64;
    private String path;
    private String randomName = "NO";
    private String fileName;
}
