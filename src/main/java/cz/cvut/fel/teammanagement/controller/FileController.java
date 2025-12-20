package cz.cvut.fel.teammanagement.controller;

import cz.cvut.fel.teammanagement.dto.FileDTO;
import cz.cvut.fel.teammanagement.model.File;
import cz.cvut.fel.teammanagement.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("files")
public class FileController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/getAllFiles")
    public ResponseEntity<List<FileDTO>> getAllFiles() {
        List<File> files = fileService.getAll();
        return ResponseEntity.ok(files.stream().map(FileDTO::new).toList());
    }

    @GetMapping("/getFile/{fileId}")
    public ResponseEntity<FileDTO> getFile(@PathVariable Long fileId) {
        File file = fileService.getById(fileId);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new FileDTO(file));
    }

    @PostMapping("/createFile")
    public ResponseEntity<FileDTO> createFile(@RequestBody FileDTO fileDTO) {
        File file = new File();
        file.setName(fileDTO.name());
        file.setUploadDate(fileDTO.uploadDate());
        file = fileService.create(file);
        return ResponseEntity.ok(new FileDTO(file));
    }

    @PutMapping("/updateFile/{fileId}")
    public ResponseEntity<FileDTO> updateFile(@PathVariable Long fileId, @RequestBody FileDTO fileDTO) {
        File file = fileService.getById(fileId);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        file.setName(fileDTO.name());
        file.setUploadDate(fileDTO.uploadDate());
        File updated = fileService.update(file);
        return ResponseEntity.ok(new FileDTO(updated));
    }

    @DeleteMapping("/deleteFile/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long fileId) {
        if (fileService.delete(fileId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
