package com.Dropbox.service;

import com.Dropbox.exception.FileNotFoundException;
import com.Dropbox.exception.FileStorageException;
import com.Dropbox.model.FileMetadata;
import com.Dropbox.repository.FileMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    private static final String FILE_STORAGE_PATH = "uploaded_files/";

    @Autowired
    private FileMetadataRepository repository;

    public FileService() {
        try {
            Files.createDirectories(Paths.get(FILE_STORAGE_PATH));
        } catch (Exception e) {
            throw new FileStorageException("Could not create storage folder");
        }
    }

    public FileMetadata uploadFile(String fileName, byte[] fileData) {
        try {
            Path filePath = Paths.get(FILE_STORAGE_PATH + fileName);
            Files.write(filePath, fileData);

            FileMetadata fileMetadata = new FileMetadata(fileName, fileData.length, Files.probeContentType(filePath), LocalDateTime.now());
            return repository.save(fileMetadata);
        } catch (Exception e) {
            throw new FileStorageException("Could not store file");
        }
    }

    public Optional<FileMetadata> getFileMetadata(Long fileId) {
        return repository.findById(fileId);
    }

    public byte[] getFileData(String fileName) {
        try {
            Path filePath = Paths.get(FILE_STORAGE_PATH + fileName);
            return Files.readAllBytes(filePath);
        } catch (Exception e) {
            throw new FileNotFoundException("File not found");
        }
    }

    public List<FileMetadata> listFiles() {
        return repository.findAll();
    }

    public void deleteFile(Long fileId) {
        Optional<FileMetadata> metadata = repository.findById(fileId);
        if (metadata.isPresent()) {
            try {
                Path filePath = Paths.get(FILE_STORAGE_PATH + metadata.get().getFileName());
                Files.deleteIfExists(filePath);
                repository.deleteById(fileId);
            } catch (Exception e) {
                throw new FileStorageException("Could not delete file");
            }
        } else {
            throw new FileNotFoundException("File not found");
        }
    }

    public FileMetadata updateFile(Long fileId, String fileName, byte[] fileData) {
        Optional<FileMetadata> metadataOpt = repository.findById(fileId);
        if (metadataOpt.isPresent()) {
            try {
                FileMetadata metadata = metadataOpt.get();
                Files.write(Paths.get(FILE_STORAGE_PATH + fileName), fileData);
                metadata.setFileName(fileName);
                metadata.setSize(fileData.length);
                metadata.setCreatedAt(LocalDateTime.now());
                return repository.save(metadata);
            } catch (Exception e) {
                throw new FileStorageException("Could not update file");
            }
        } else {
            throw new FileNotFoundException("File not found");
        }
    }
}
