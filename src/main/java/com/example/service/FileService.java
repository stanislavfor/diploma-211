package com.example.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    //    private static final String UPLOAD_DIR = "src/main/resources/static/images";
    private static final String UPLOAD_DIR = "uploads/images";

    public boolean deleteFile(String filename) {
        Path filePath = Paths.get(UPLOAD_DIR, filename);

        try {
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
