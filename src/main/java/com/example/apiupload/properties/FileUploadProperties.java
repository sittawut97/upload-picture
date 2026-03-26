package com.example.apiupload.properties;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@ConfigurationProperties(prefix = "app.upload")
@Validated
public class FileUploadProperties {
    
    @NotNull
    private String maxFileSize = "10MB";
    
    @NotEmpty
    private List<String> allowedContentTypes = Arrays.asList(
        "image/jpeg",
        "image/jpg", 
        "image/png",
        "image/gif",
        "image/webp",
        "image/svg+xml",
        "application/pdf"
    );
    
    @NotNull
    private Duration urlExpiration = Duration.ofHours(1);
    
    private int maxUploadRequestsPerMinute = 60;
    
    private boolean enableVirusScanning = false;
       
    public String getMaxFileSize() {
        return maxFileSize;
    }
    
    public void setMaxFileSize(String maxFileSize) {
        this.maxFileSize = maxFileSize;
    }
    
    public long getMaxFileSizeInBytes() {
        return DataSize.parse(maxFileSize).toBytes();
    }
    
    public List<String> getAllowedContentTypes() {
        return allowedContentTypes;
    }
    
    public void setAllowedContentTypes(List<String> allowedContentTypes) {
        this.allowedContentTypes = allowedContentTypes;
    }
    
    public Duration getUrlExpiration() {
        return urlExpiration;
    }
    
    public void setUrlExpiration(Duration urlExpiration) {
        this.urlExpiration = urlExpiration;
    }
    
    public int getMaxUploadRequestsPerMinute() {
        return maxUploadRequestsPerMinute;
    }
    
    public void setMaxUploadRequestsPerMinute(int maxUploadRequestsPerMinute) {
        this.maxUploadRequestsPerMinute = maxUploadRequestsPerMinute;
    }
    
    public boolean isEnableVirusScanning() {
        return enableVirusScanning;
    }
    
    public void setEnableVirusScanning(boolean enableVirusScanning) {
        this.enableVirusScanning = enableVirusScanning;
    }
}
