package com.example.pumb_test_task.controller;

import com.example.pumb_test_task.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/files")
public class FileUploadController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    @Operation(summary = "Upload a file", description = "Uploads a file and processes it to store animal data in the database.")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }
        try {
            fileService.uploadFile(file);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{filename}")
                    .buildAndExpand(file.getOriginalFilename())
                    .toUri();

            return ResponseEntity.created(location).build();

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
