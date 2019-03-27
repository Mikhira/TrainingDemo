package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileStorageProperties {

    @Value("${file.upload-dir}")
    private String directoryToUpload;

    public String getDirectoryToUpload() {
        return directoryToUpload;
    }

}
