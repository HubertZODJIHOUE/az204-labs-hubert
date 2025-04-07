package com.azure.medical.back.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AzureBlobService {

    @Value("${azure.storage.connection-string}")
    private String connectionString;

    private String blobUrl =null;

    @Value("${azure.storage.container-name}")
    private String containerName;

    public String uploadFile(MultipartFile file) {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();

        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        if (!containerClient.exists()) {
            containerClient.create();
        }
        if(file.getSize()>0  && file.getOriginalFilename() != null){
            BlobClient blobClient = containerClient.getBlobClient(file.getOriginalFilename());
            try {
                blobClient.upload(file.getInputStream(), file.getSize(), true);
                 this.blobUrl = blobClient.getBlobUrl();
            } catch (IOException e) {
                throw new RuntimeException("Erreur pendant l'upload", e);
            }
        }
        return blobUrl;


    }



    public  void deleteFile(String fileName) {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();

        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        if (!containerClient.exists()) {
            containerClient.create();
        }
        BlobClient blobClient = containerClient.getBlobClient(fileName);
        blobClient.delete();

    }

    public  void updateFile(String fileName, MultipartFile file) {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();

        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        if (!containerClient.exists()) {
            containerClient.create();
        }
        if(file.getSize()>0  && file.getOriginalFilename() != null){
            BlobClient blobClient = containerClient.getBlobClient(fileName);
            try {
                blobClient.upload(file.getInputStream(), file.getSize(), true);
            } catch (IOException e) {
                throw new RuntimeException("Erreur pendant l'upload", e);
            }
        }
    }


}
