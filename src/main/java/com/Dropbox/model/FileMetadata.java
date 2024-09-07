package com.Dropbox.model;





import lombok.Data;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private long size;
    private String type;
    private LocalDateTime createdAt;

    public FileMetadata() {}

    public FileMetadata(String fileName, long size, String type, LocalDateTime createdAt) {
        this.fileName = fileName;
        this.size = size;
        this.type = type;
        this.createdAt = createdAt;
    }

}
