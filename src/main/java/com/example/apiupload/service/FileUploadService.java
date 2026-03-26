package com.example.apiupload.service;

import com.example.apiupload.dto.UploadResponse;
import com.example.apiupload.exception.FileUploadException;
import com.example.apiupload.properties.FileUploadProperties;
import com.example.apiupload.repository.FileStorageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class FileUploadService {

    private final FileStorageRepository fileStorageRepository;
    private final FileUploadProperties properties;
    
    public FileUploadService(FileStorageRepository fileStorageRepository, FileUploadProperties properties) {
        this.fileStorageRepository = fileStorageRepository;
        this.properties = properties;
    }

    public UploadResponse uploadFile(MultipartFile file) {
        validateFile(file);
        
        try {
            String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());
            String objectKey = generateObjectKey(uniqueFileName);
            
            fileStorageRepository.upload(objectKey, file);
            
            String fileUrl = fileStorageRepository.generatePresignedUrl(objectKey, properties.getUrlExpiration());
            
            return new UploadResponse(fileUrl, uniqueFileName, file.getSize());
            
        } catch (Exception e) {
            throw new FileUploadException("Failed to upload file: " + e.getMessage(), e);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileUploadException("File cannot be empty");
        }
        
        if (file.getSize() > properties.getMaxFileSizeInBytes()) {
            throw new FileUploadException("File size exceeds maximum limit of " + properties.getMaxFileSize());
        }
        
        String contentType = file.getContentType();
        if (contentType == null || !properties.getAllowedContentTypes().contains(contentType)) {
            throw new FileUploadException("Invalid file type. Only images and PDF files are allowed");
        }
    }

    private String generateUniqueFileName(String originalFilename) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String uuid = UUID.randomUUID().toString().replace("-", "");

        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        
        return timestamp + "_" + uuid + extension;
    }

    private String generateObjectKey(String fileName) {
        LocalDateTime now = LocalDateTime.now();
        String year = String.format("%04d", now.getYear());
        String month = String.format("%02d", now.getMonthValue());
        
        return "uploads/" + year + "/" + month + "/" + fileName;
    }

}
