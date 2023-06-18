package com.xpense.services.app.service;

import com.xpense.services.app.FileStorageProperties;
import com.xpense.services.app.exceptions.FileStorageException;
import com.xpense.services.app.exceptions.MyFileNotFoundException;
import com.xpense.services.app.models.FileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties){
        fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try{
            Files.createDirectories(this.fileStorageLocation);
        }catch (Exception ex){
            throw new FileStorageException("Could not create a directory to upload files");
        }
    }

    public String storeFile(MultipartFile file){
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try{
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException ioe){
            throw new FileStorageException("Could not store the file "+fileName + ". Please try again",ioe);
        }
        return fileName;
    }

    public Resource loadFileAsResource(String fileName){
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()){
                return resource;
            }else{
                throw new MyFileNotFoundException("File not found "+ fileName);
            }
        }catch (MalformedURLException mfe){
            throw new MyFileNotFoundException("File not found "+ fileName);
        }
    }

    public FileResponse getFileResponse(String fileName, MultipartFile file ){
        String fileDownloadUri  = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/v1/files/")
                .path(fileName)
                .toUriString();

        return new FileResponse(fileName,file.getContentType(), fileDownloadUri,file.getSize());
    }
}
