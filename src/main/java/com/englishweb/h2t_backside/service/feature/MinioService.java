package com.englishweb.h2t_backside.service.feature;

import com.englishweb.h2t_backside.service.feature.impl.MinioServiceImpl;

public interface MinioService {

    // Create bucket if not exist
    void createBucket();

    String uploadFile(String path, String fileName, byte[] bytes, String mimeType, MinioServiceImpl.RandomName randomName);

    String uploadFileFromBase64(String path, String base64, String fileName, MinioServiceImpl.RandomName randomName);

    byte[] getFile(String objectName);

    void deleteFile(String objectName);
}
