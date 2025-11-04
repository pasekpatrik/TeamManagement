package cz.cvut.fel.teammanagement.repository;

import cz.cvut.fel.teammanagement.model.Document;
import org.springframework.stereotype.Repository;

@Repository
public class DocumentDAO extends AbstractDAO<Document> {
    public DocumentDAO() {
        super(Document.class);
    }
}

