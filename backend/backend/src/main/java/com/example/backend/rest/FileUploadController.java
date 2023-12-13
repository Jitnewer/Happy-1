package com.example.backend.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;


@Controller
public class FileUploadController {

    @PostMapping("/upload/profilePic")
    public ResponseEntity<Object> handleFileUpload(
            @RequestPart("file") MultipartFile file,
            @RequestPart("userName") String userName,
            @RequestPart("userId") String userId
    ) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload");
        }

        try {
            // Get the absolute path where image will be stored
            String uploadFolderPath = "frontend/src/assets/profilePic";

            // Create the folder if it doesn't exist
            File uploadFolder = new File(uploadFolderPath);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            // Extract the file extension from the content type
            String fileExtension = getFileExtension(file.getOriginalFilename());

            // Generate a custom filename using user's first name and ID
            String customFilename = userName + "_" + userId + fileExtension;

            // Save the file to the upload folder
            Path filePath = Paths.get(uploadFolderPath, customFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.status(HttpStatus.OK).body(
                    Map.of(
                            "message", "Profile image uploaded successfully",
                            "filePath", "assets/profilePic/" + customFilename
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload the file");
        }
    }

    public static String getFileExtension(String fileName) {
        // Find the last occurrence of dot in the file name
        int lastDotIndex = fileName.lastIndexOf('.');

        // Check if a dot is found and it is not the last character
        if (lastDotIndex != -1 && lastDotIndex < fileName.length() - 1) {
            // Extract the substring starting from the last dot to the end of the string
            return "." + fileName.substring(lastDotIndex + 1).toLowerCase();
        }

        // Return null if no file extension is found
        return null;
    }
}
