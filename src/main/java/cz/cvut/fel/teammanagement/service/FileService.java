package cz.cvut.fel.teammanagement.service;

import cz.cvut.fel.teammanagement.model.File;
import cz.cvut.fel.teammanagement.repository.FileDAO;
import cz.cvut.fel.teammanagement.repository.DocumentDAO;
import cz.cvut.fel.teammanagement.repository.ImageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class FileService extends AbstractService<File> {
    protected final DocumentDAO documentDAO;
    protected final ImageDAO imageDAO;

    @Autowired
    public FileService(FileDAO fileDAO, DocumentDAO documentDAO, ImageDAO imageDAO) {
        super(fileDAO);
        this.documentDAO = documentDAO;
        this.imageDAO = imageDAO;
    }

    @Transactional(readOnly = true)
    public List<File> findFilesUploadedAfter(LocalDate date) {
        return dao.findAll().stream()
            .filter(f -> f.getUploadDate() != null && f.getUploadDate().isAfter(date))
            .toList();
    }

    @Transactional(readOnly = true)
    public List<File> findFilesByType(String type) {
        return dao.findAll().stream()
            .filter(f -> f.getClass().getSimpleName().equalsIgnoreCase(type))
            .toList();
    }
}
