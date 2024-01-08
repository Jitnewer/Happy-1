package com.example.backend.rest;

import io.imagekit.sdk.exceptions.*;
import io.imagekit.sdk.models.FileCreateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import io.imagekit.sdk.utils.Utils;


@RestController
@RequestMapping("image")
public class FileUploadController {

    @PostMapping("/profilePic")
    public ResponseEntity<Object> uploadProfilePic(
            @RequestPart("file") MultipartFile file,
            @RequestPart("userName") String userName,
            @RequestPart("userId") String userId
    ) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload");
            }

            String customFileName = uploadFile(file, userName, userId, "/profilePic");
            return ResponseEntity.status(HttpStatus.OK).body(
                    Map.of(
                            "message", "Image uploaded successfully",
                            "filePath", customFileName
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload the file");
        }
    }

    @PostMapping("/eventPic")
    public ResponseEntity<Object> uploadEventPic (
            @RequestPart("file") MultipartFile file,
            @RequestPart("eventId") String eventId
    ) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload");
        }

        try {
            String customFileName = uploadFile(file, "event", eventId, "/eventPic");
            return ResponseEntity.status(HttpStatus.OK).body(
                    Map.of(
                            "message", "Event image uploaded successfully",
                            "filePath", customFileName
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload the file");
        }
    }

    @PostMapping("/researchPic")
    public ResponseEntity<Object> uploadResearchPic (
            @RequestPart("file") MultipartFile file,
            @RequestPart("researchId") String researchId
    ) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload");
        }

        try {
            String customFileName = uploadFile(file, "research", researchId, "/researchPic");
            return ResponseEntity.status(HttpStatus.OK).body(
                    Map.of(
                            "message", "Research image uploaded successfully",
                            "filePath", customFileName
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload the file");
        }
    }

    @PostMapping("/challengePic")
    public ResponseEntity<Object> uploadChallengePic (
            @RequestPart("file") MultipartFile file,
            @RequestPart("challengeId") String challengeId
    ) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload");
        }

        try {
            String customFileName = uploadFile(file, "research", challengeId, "/challengePic");
            return ResponseEntity.status(HttpStatus.OK).body(
                    Map.of(
                            "message", "Challenge image uploaded successfully",
                            "filePath", customFileName
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload the file");
        }
    }

    @PostMapping("/networkPic")
    public ResponseEntity<Object> uploadNetworkPic (
            @RequestPart("file") MultipartFile file,
            @RequestPart("networkId") String networkId
    ) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload");
        }

        try {
            String customFileName = uploadFile(file, "network", networkId, "/networkPic");
            return ResponseEntity.status(HttpStatus.OK).body(
                    Map.of(
                            "message", "Challenge image uploaded successfully",
                            "filePath", customFileName
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload the file");
        }
    }


    @DeleteMapping("/delete")
    public ResponseEntity<Object> deletePicture (@RequestPart("imagePath") String imagePath) {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Cannot delete image from file path: " + imagePath));
        }

        try {
//            ImageKit.getInstance().deleteFile(imagePath); // Will have to figure out how to delete from ImageKit without knowing the ID of the file
            return ResponseEntity.status(HttpStatus.OK).body(
                    Map.of(
                            "message", "Image deleted successfully"
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete image: " + e);
        }
    }

    private String uploadFile(MultipartFile file, String name, String id, String folder) {
        try {
            String uploadFolderPath = "assets/" + folder;

//            File uploadFolder = new File(uploadFolderPath);
//            if (!uploadFolder.exists()) {
//                uploadFolder.mkdirs();
//            }

            String fileExtension = getFileExtension(file.getOriginalFilename());

            // Generate a custom filename using user's first name and ID
            String customFileName = name + "_" + id + fileExtension;

            // Save the file to the upload folder
//            Path filePath = Paths.get(uploadFolderPath, customFileName);
//            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            FileCreateRequest fileCreateRequest = new FileCreateRequest(
                    file.getBytes(),
                    customFileName
            );
            fileCreateRequest.setFolder(uploadFolderPath);
            String result = ImageKit.getInstance().upload(fileCreateRequest).getFilePath();

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFileExtension(String fileName) {
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
