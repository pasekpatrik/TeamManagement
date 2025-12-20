package cz.cvut.fel.teammanagement.dto;

import cz.cvut.fel.teammanagement.model.Document;
import cz.cvut.fel.teammanagement.model.File;

import java.time.LocalDate;

public record FileDTO (Long id, String name, LocalDate uploadDate){
    public FileDTO(File file) {
        this(
            file.getId(),
            file.getName(),
            file.getUploadDate()
        );
    }
}
