package com.example.apiupload.repository;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.time.Duration;

public interface FileStorageRepository {
    
    void upload(String objectKey, MultipartFile file) throws IOException, S3Exception;

    String generatePresignedUrl(String objectKey, Duration expiration);

    void delete(String objectKey) throws S3Exception;
    
    boolean exists(String objectKey);
}
