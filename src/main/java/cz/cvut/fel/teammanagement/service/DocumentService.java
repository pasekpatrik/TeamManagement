package cz.cvut.fel.teammanagement.service;

import cz.cvut.fel.teammanagement.model.Document;
import cz.cvut.fel.teammanagement.repository.DocumentDAO;
import cz.cvut.fel.teammanagement.repository.FileDAO;
import cz.cvut.fel.teammanagement.repository.ImageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DocumentService extends FileService {
    @Autowired
    public DocumentService(DocumentDAO documentDAO, FileDAO fileDAO, ImageDAO imageDAO) {
        super(fileDAO, documentDAO, imageDAO);
    }

    @Transactional(readOnly = true)
    public List<Document> findDocumentsByName(String namePart) {
        return documentDAO.findAll().stream()
                .filter(d -> d.getName() != null && d.getName().contains(namePart))
                .toList();
    }
}