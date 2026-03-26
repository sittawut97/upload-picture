package com.example.apiupload.controller;

import com.example.apiupload.dto.UploadResponse;
import com.example.apiupload.service.FileUploadService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/upload")
    public ResponseEntity<UploadResponse> uploadFile(
            @RequestParam("file") 
            @Valid 
            @NotNull(message = "File cannot be null")
            MultipartFile file) {
        
        UploadResponse response = fileUploadService.uploadFile(file);
        return ResponseEntity.ok(response);
    }
}
