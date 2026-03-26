package com.example.apiupload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ApiUploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiUploadApplication.class, args);
    }
}
