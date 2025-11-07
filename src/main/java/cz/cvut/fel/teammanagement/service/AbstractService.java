package cz.cvut.fel.teammanagement.service;

import cz.cvut.fel.teammanagement.model.AbstractEntity;
import cz.cvut.fel.teammanagement.repository.AbstractDAO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class AbstractService<T extends AbstractEntity> {
    protected final AbstractDAO<T> dao;

    protected AbstractService(AbstractDAO<T> dao) {
        this.dao = dao;
    }

    @Transactional
    public T create(T entity) {
        dao.persist(entity);
        return entity;
    }

    @Transactional(readOnly = true)
    public T getById(Long id) {
        return dao.find(id);
    }

    @Transactional(readOnly = true)
    public List<T> getAll() {
        return dao.findAll();
    }

    @Transactional
    public T update(T entity) {
        return dao.update(entity);
    }

    @Transactional
    public boolean delete(Long id) {
        T entity = dao.find(id);
        if (entity != null) {
            dao.delete(entity);
            return true;
        }
        return false;
    }
}

