package com.example.demo.dto;

public class UploadFileResponse {

    private String fileName;
    private Long size;

    public UploadFileResponse(String fileName, Long size) {
        this.fileName = fileName;
        this.size = size;
    }

    public void showText() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
