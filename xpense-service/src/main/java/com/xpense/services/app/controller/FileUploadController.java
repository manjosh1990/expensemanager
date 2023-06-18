package com.xpense.services.app.controller;

import com.xpense.services.app.models.FileResponse;
import com.xpense.services.app.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("v1/files")
@Slf4j
public class FileUploadController {
    @Autowired
    private FileStorageService fileStorageService;
    @PutMapping
    public ResponseEntity<FileResponse> uploadFile(@RequestParam("file") MultipartFile file){
        String fileName = fileStorageService.storeFile(file);
        FileResponse fileResponse = fileStorageService.getFileResponse(fileName,file);
        return new ResponseEntity<FileResponse>(fileResponse, HttpStatus.OK);
    }

    @GetMapping("/{fileName:.+}")
    public  ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request){
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        String contentType = null;
        try{
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch (IOException ex){
            log.debug("could not determine the mime type");
        }
        if(contentType == null){
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
    }

}
