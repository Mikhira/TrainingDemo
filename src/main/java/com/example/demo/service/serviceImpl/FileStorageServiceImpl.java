package com.example.demo.service.serviceImpl;

import com.example.demo.config.FileStorageProperties;
import com.example.demo.exception.FileStorageException;
import com.example.demo.exception.MyFileNotFoundException;
import com.example.demo.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) throws IOException {
        this.fileStorageLocation = createLocation(fileStorageProperties);
        Files.createDirectories(fileStorageLocation);
    }

    private Path createLocation(FileStorageProperties fileStorageProperties) {
        return Paths.get(fileStorageProperties.getDirectoryToUpload())
                .toAbsolutePath().normalize();
    }

    public String storeFile(MultipartFile file) {
        String fileName = normalizeFileName(file);
        validateFileName(fileName);
        try {
            saveFileToTargetLocationOrReplaceWithSameName(file, fileName);
        } catch (IOException e) {

        }
        return fileName;
    }

    private void saveFileToTargetLocationOrReplaceWithSameName(MultipartFile file, String fileName) throws IOException {
        Path targetLocation = fileStorageLocation.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
    }

    private void validateFileName(String fileName) {
        if(fileName.contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        }
    }

    private String normalizeFileName(MultipartFile file) {
        return StringUtils.cleanPath(file.getOriginalFilename());
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

}
