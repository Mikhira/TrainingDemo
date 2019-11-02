package com.example.demo.controller;

import com.example.demo.dto.UploadFileResponse;
import com.example.demo.service.FileStorageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class FileController {

    private final Logger LOGGER = LoggerFactory.getLogger(FileController.class.getSimpleName());

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("${rest-endpoints.save.file}")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        LOGGER.info("Param01 : {}", file.getName());

        final String fileName = fileStorageService.storeFile(file);
        final Integer fileSizeInKb = Math.toIntExact(file.getSize() / 1000);

        LOGGER.info("Multipart file with name {} and size {} kb " +
                "was saved successfully", fileName, fileSizeInKb);
        return new UploadFileResponse(fileName, file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(
            @RequestParam("param01")  MultipartFile file01,
            @RequestParam("param02") MultipartFile file02) {
        LOGGER.info("Param 01 : {}, Param 02 : {}", file01.getName(), file02.getName());
        return Arrays.asList(file01, file02)
                .stream()
                .filter(fileObj -> Objects.nonNull(fileObj))
                .map(fileToSave -> uploadFile(fileToSave))
               .collect(Collectors.toList());
    }

}

