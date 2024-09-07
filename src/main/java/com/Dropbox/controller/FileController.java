package com.Dropbox.controller;

import com.Dropbox.exception.FileNotFoundException;
import com.Dropbox.model.FileMetadata;
import com.Dropbox.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<FileMetadata> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        FileMetadata metadata = fileService.uploadFile(file.getOriginalFilename(), file.getBytes());
        return ResponseEntity.ok(metadata);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long fileId) {
        Optional<FileMetadata> metadataOpt = fileService.getFileMetadata(fileId);
        if (metadataOpt.isPresent()) {
            byte[] fileData = fileService.getFileData(metadataOpt.get().getFileName());
            return ResponseEntity.ok(fileData);
        }
        throw new FileNotFoundException("File not found");
    }

    @PutMapping("/{fileId}")
    public ResponseEntity<FileMetadata> updateFile(@PathVariable Long fileId, @RequestParam("file") MultipartFile file) throws IOException {
        FileMetadata metadata = fileService.updateFile(fileId, file.getOriginalFilename(), file.getBytes());
        return ResponseEntity.ok(metadata);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long fileId) {
        fileService.deleteFile(fileId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<FileMetadata>> listFiles() {
        return ResponseEntity.ok(fileService.listFiles());
    }
}
