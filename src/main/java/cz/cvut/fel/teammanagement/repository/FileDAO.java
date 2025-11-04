package cz.cvut.fel.teammanagement.repository;

import cz.cvut.fel.teammanagement.model.File;
import org.springframework.stereotype.Repository;

@Repository
public class FileDAO extends AbstractDAO<File> {
    public FileDAO() {
        super(File.class);
    }
}

