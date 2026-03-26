package com.example.apiupload.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class UploadResponse {

    @JsonProperty("url")
    private final String url;

    @JsonProperty("fileName")
    private final String fileName;

    @JsonProperty("size")
    private final Long size;

    public UploadResponse() {
        this.url = null;
        this.fileName = null;
        this.size = null;
    }

    public UploadResponse(String url, String fileName, Long size) {
        this.url = url;
        this.fileName = fileName;
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public String getFileName() {
        return fileName;
    }

    public Long getSize() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UploadResponse that = (UploadResponse) o;
        return Objects.equals(url, that.url) &&
                Objects.equals(fileName, that.fileName) &&
                Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, fileName, size);
    }

    @Override
    public String toString() {
        return "UploadResponse{" +
                "url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", size=" + size +
                '}';
    }
}
