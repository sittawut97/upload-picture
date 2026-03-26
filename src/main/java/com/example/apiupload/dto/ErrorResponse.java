package com.example.apiupload.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class ErrorResponse {

    @JsonProperty("message")
    private final String message;

    public ErrorResponse() {
        this.message = null;
    }

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorResponse that = (ErrorResponse) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "message='" + message + '\'' +
                '}';
    }
}
