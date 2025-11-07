package cz.cvut.fel.teammanagement.service;

import cz.cvut.fel.teammanagement.model.File;
import cz.cvut.fel.teammanagement.model.Document;
import cz.cvut.fel.teammanagement.model.Image;
import cz.cvut.fel.teammanagement.model.Team;
import cz.cvut.fel.teammanagement.repository.FileDAO;
import cz.cvut.fel.teammanagement.repository.DocumentDAO;
import cz.cvut.fel.teammanagement.repository.ImageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class FileService {
    private final FileDAO fileDAO;
    protected final DocumentDAO documentDAO;
    protected final ImageDAO imageDAO;

    @Autowired
    public FileService(FileDAO fileDAO, DocumentDAO documentDAO, ImageDAO imageDAO) {
        this.fileDAO = fileDAO;
        this.documentDAO = documentDAO;
        this.imageDAO = imageDAO;
    }

    @Transactional
    public File createFile(File file) {
        fileDAO.persist(file);
        return file;
    }

    @Transactional(readOnly = true)
    public File findFileById(Long id) {
        return fileDAO.find(id);
    }

    @Transactional(readOnly = true)
    public List<File> getAllFiles() {
        return fileDAO.findAll();
    }

    @Transactional
    public File updateFile(File file) {
        return fileDAO.update(file);
    }

    @Transactional
    public boolean deleteFile(Long id) {
        File file = fileDAO.find(id);
        if (file != null) {
            fileDAO.delete(file);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<File> findFilesUploadedAfter(LocalDate date) {
        return fileDAO.findAll().stream()
            .filter(f -> f.getUploadDate() != null && f.getUploadDate().isAfter(date))
            .toList();
    }

    @Transactional
    public Document createDocument(Document document) {
        documentDAO.persist(document);
        return document;
    }

    @Transactional(readOnly = true)
    public List<Document> getAllDocuments() {
        return documentDAO.findAll();
    }

    @Transactional
    public Image createImage(Image image) {
        imageDAO.persist(image);
        return image;
    }

    @Transactional(readOnly = true)
    public List<Image> getAllImages() {
        return imageDAO.findAll();
    }

    @Transactional(readOnly = true)
    public List<File> findFilesByType(String type) {
        return fileDAO.findAll().stream()
            .filter(f -> f.getClass().getSimpleName().equalsIgnoreCase(type))
            .toList();
    }
}
