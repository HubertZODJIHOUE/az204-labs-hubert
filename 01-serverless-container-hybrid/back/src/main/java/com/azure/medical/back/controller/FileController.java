package com.azure.medical.back.controller;

import com.azure.medical.back.services.AzureBlobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/api/files")
public class FileController {
     @Autowired
     private AzureBlobService azureBlobService;


     @PostMapping("/upload")
     public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
         String blobUrl = azureBlobService.uploadFile(file);
         return ResponseEntity.ok(blobUrl);
     }

     @DeleteMapping("/delete/{fileName}")
     public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
         azureBlobService.deleteFile(fileName);
         return ResponseEntity.ok("File deleted successfully");
     }
}
