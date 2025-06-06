package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.service.feature.MinioService;
import io.minio.*;
import io.minio.http.Method;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class MinioServiceImpl implements MinioService {

    @Value("${minio.bucketName}")
    private String bucketName;

    @Value("${minio.endpoint}")
    private String endpoint;

    @Autowired
    private MinioClient minioClient;

    @Override
    public void createBucket() {
        log.info("Attempting to create or verify bucket: {}", bucketName);
        try {
            boolean bucketExists = minioClient.bucketExists(
                    BucketExistsArgs.builder().bucket(bucketName).build()
            );
            if (!bucketExists) {
                log.info("Bucket '{}' does not exist. Creating new bucket.", bucketName);
                minioClient.makeBucket(
                        MakeBucketArgs.builder().bucket(bucketName).build()
                );
                log.info("Successfully created bucket: {}", bucketName);
            } else {
                log.debug("Bucket '{}' already exists", bucketName);
            }
        } catch (Exception e) {
            log.error("Failed to create bucket '{}': {}", bucketName, e.getMessage(), e);
            throw new CreateResourceException(
                    new HashMap<String, String>(){{
                        put("bucketName", bucketName);
                    }},
                    e.getMessage(),
                    ErrorApiCodeContent.BUCKET_CREATED_FAIL,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    SeverityEnum.HIGH);
        }
    }

    @Override
    public String uploadFile(String path, String fileName, byte[] bytes, String mimeType, RandomName randomName) {
        log.info("Uploading file: {} to path: {}, with mime type: {}, randomName: {}", fileName, path, mimeType, randomName);

        // Generate a unique file name
        String uniqueFileName = fileName;
        if(randomName.equals(RandomName.YES)) {
            String uuid = UUID.randomUUID().toString();
            uniqueFileName += "-" + uuid;
            log.debug("Generated unique filename with UUID: {}", uniqueFileName);
        }

        if (mimeType == null || mimeType.isEmpty()) {
            mimeType = "application/octet-stream";
            log.debug("No mime type provided, defaulting to: {}", mimeType);
        }

        // Ensure path has trailing slash if not empty
        if (path != null && !path.isEmpty() && !path.endsWith("/")) {
            path += "/";
            log.debug("Path did not end with '/', appended slash: {}", path);
        }

        // Create the full object name (path + filename)
        String objectName = (path != null && !path.isEmpty()) ? path + uniqueFileName : uniqueFileName;
        log.debug("Full object name created: {}", objectName);

        try {
            // Ensure bucket exists
            createBucket();

            log.debug("Uploading file to Minio. Size: {} bytes", bytes.length);

            // Upload the file
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(new java.io.ByteArrayInputStream(bytes), bytes.length, -1)
                            .contentType(mimeType)
                            .build()
            );

            log.info("Successfully uploaded file: {} to bucket: {}", objectName, bucketName);

            // Generate and return the URL for the uploaded file
            String fileUrl = getStorageUrl(objectName);
            log.info("Generated storage URL: {}", fileUrl);
            return fileUrl;
        } catch (Exception e) {
            log.error("Failed to upload file: {} to bucket: {}. Error: {}", objectName, bucketName, e.getMessage(), e);
            String finalMimeType = mimeType;
            throw new CreateResourceException(
                    new HashMap<String, String>(){{
                        put("minType", finalMimeType);
                        put("fileName", fileName);
                        put("objectName", objectName);
                        put("bucketName", bucketName);
                    }},
                    e.getMessage(),
                    ErrorApiCodeContent.FILE_UPLOAD_FAIL,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    SeverityEnum.MEDIUM);
        }
    }

    @Override
    public String uploadFileFromBase64(String path, String base64, String fileName, RandomName randomName) {
        log.info("Uploading file from Base64 string. Path: {}, Filename: {}, RandomName: {}", path, fileName, randomName);
        try {
            String[] parts = base64.split(",");
            String base64Data = parts.length > 1 ? parts[1] : parts[0];
            log.debug("Base64 string split into {} parts", parts.length);

            // Decode base64 string to bytes
            byte[] fileBytes = Base64.getDecoder().decode(base64Data);
            log.debug("Decoded Base64 data into {} bytes", fileBytes.length);

            String mimeType = determineMimeType(base64);
            log.debug("Determined mime type: {}", mimeType);

            if (fileName == null || fileName.isEmpty()) {
                fileName = "file-" + System.currentTimeMillis();
                log.debug("No filename provided, generated default filename: {}", fileName);
            }

            return uploadFile(path, fileName, fileBytes, mimeType, randomName);
        } catch (IllegalArgumentException e) {
            log.error("Failed to process Base64 string: {}", e.getMessage(), e);
            throw new CreateResourceException(
                    new HashMap<String, String>() {{
                        put("error", "Invalid base64 string");
                    }},
                    e.getMessage(),
                    ErrorApiCodeContent.FILE_UPLOAD_FAIL,
                    HttpStatus.BAD_REQUEST,
                    SeverityEnum.LOW);
        }
    }

    private String determineMimeType(String base64) {
        log.debug("Determining mime type from Base64 string");
        // Example: data:mimetype;base64,actualdata
        if (base64.startsWith("data:") && base64.contains(";base64,")) {
            String mimeType = base64.substring(5, base64.indexOf(";base64,"));
            log.debug("Extracted mime type: {}", mimeType);
            return mimeType.isEmpty() ? "application/octet-stream" : mimeType;
        }
        log.debug("Could not determine mime type from Base64 string, using default");
        return "application/octet-stream";
    }

    private String getStorageUrl(String objectName) {
        String url = String.format("%s/%s/%s", endpoint, bucketName, objectName);
        log.debug("Generated storage URL: {}", url);
        return url;
    }

    @Override
    public byte[] getFile(String objectName) {
        log.info("Attempting to retrieve file: {} from bucket: {}", objectName, bucketName);
        try {
            InputStream fileStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );

            byte[] fileBytes = fileStream.readAllBytes();
            log.info("Successfully retrieved file: {}. Size: {} bytes", objectName, fileBytes.length);

            fileStream.close();
            log.debug("Closed input stream after reading file");

            return fileBytes;
        } catch (Exception e) {
            log.error("Failed to retrieve file: {} from bucket: {}. Error: {}", objectName, bucketName, e.getMessage(), e);
            throw new CreateResourceException(
                    new HashMap<String, String>() {{
                        put("objectName", objectName);
                        put("bucketName", bucketName);
                    }},
                    e.getMessage(),
                    ErrorApiCodeContent.FILE_DOWNLOAD_FAIL,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    SeverityEnum.MEDIUM);
        }
    }

    @Override
    public void deleteFile(String objectName) {
        log.info("Attempting to delete file: {} from bucket: {}", objectName, bucketName);
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
            log.info("Successfully deleted file: {} from bucket: {}", objectName, bucketName);
        } catch (Exception e) {
            log.error("Failed to delete file: {} from bucket: {}. Error: {}", objectName, bucketName, e.getMessage(), e);
            throw new CreateResourceException(
                    new HashMap<String, String>() {{
                        put("objectName", objectName);
                        put("bucketName", bucketName);
                    }},
                    e.getMessage(),
                    ErrorApiCodeContent.FILE_DELETE_FAIL,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    SeverityEnum.MEDIUM);
        }
    }
    public enum RandomName {
        YES,
        NO;

        public static RandomName fromString(String value) {
            if (value != null) {
                for (RandomName randomName : RandomName.values()) {
                    if (value.equalsIgnoreCase(randomName.name())) {
                        return randomName;
                    }
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }
}