package cz.cvut.fel.teammanagement.service;

import cz.cvut.fel.teammanagement.model.Image;
import cz.cvut.fel.teammanagement.repository.DocumentDAO;
import cz.cvut.fel.teammanagement.repository.FileDAO;
import cz.cvut.fel.teammanagement.repository.ImageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ImageService extends FileService {
    @Autowired
    public ImageService(ImageDAO imageDAO, FileDAO fileDAO, DocumentDAO documentDAO) {
        super(fileDAO, documentDAO, imageDAO);
    }

    @Transactional(readOnly = true)
    public List<Image> findImagesByExtension(String extension) {
        return imageDAO.findAll().stream()
            .filter(i -> i.getName() != null && i.getName().endsWith(extension))
            .toList();
    }
}
