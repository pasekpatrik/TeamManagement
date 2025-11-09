package cz.cvut.fel.teammanagement.service;

import cz.cvut.fel.teammanagement.model.Document;
import cz.cvut.fel.teammanagement.repository.DocumentDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class DocumentServiceTest {
    @Autowired
    private DocumentService documentService;
    @Autowired
    private DocumentDAO documentDAO;

    @BeforeEach
    void setUp() {
        Document doc1 = new Document();
        doc1.setName("TestDoc1");
        doc1.setUploadDate(LocalDate.now());
        documentDAO.persist(doc1);
        Document doc2 = new Document();
        doc2.setName("AnotherDoc");
        doc2.setUploadDate(LocalDate.now());
        documentDAO.persist(doc2);
    }

    @Test
    void testFindDocumentsByName_found() {
        List<Document> docs = documentService.findDocumentsByName("TestDoc");
        assertFalse(docs.isEmpty());
        assertTrue(docs.stream().anyMatch(d -> d.getName().equals("TestDoc1")));
    }

    @Test
    void testFindDocumentsByName_notFound() {
        List<Document> docs = documentService.findDocumentsByName("NonExistent");
        assertNotNull(docs);
        assertTrue(docs.isEmpty());
    }
}

