package cz.cvut.fel.teammanagement.repository;

import cz.cvut.fel.teammanagement.model.Image;
import org.springframework.stereotype.Repository;

@Repository
public class ImageDAO extends AbstractDAO<Image> {
    public ImageDAO() {
        super(Image.class);
    }
}

