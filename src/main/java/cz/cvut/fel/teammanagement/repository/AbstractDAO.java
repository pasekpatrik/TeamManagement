package cz.cvut.fel.teammanagement.repository;

import cz.cvut.fel.teammanagement.model.AbstractEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import java.util.List;
import java.util.Objects;

public abstract class AbstractDAO<T extends AbstractEntity> {
     @PersistenceContext
    protected EntityManager em;

    protected final Class<T> type;

    public AbstractDAO(Class<T> type) {
        this.type = type;
    }

    public T find(long id) {
        Objects.requireNonNull(id);
        return em.find(type, id);
    }

    public List<T> findAll() {
        try {
            return em.createQuery("SELECT e FROM " + type.getSimpleName() + " e", type).getResultList();
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    public void persist(T entity) {
        Objects.requireNonNull(entity);
        try {
            em.persist(entity);
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    public T update(T entity) {
        Objects.requireNonNull(entity);

        try {
            return em.merge(entity);
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    public void remove(T entity) {
        Objects.requireNonNull(entity);

        try {
            if (em.contains(entity)) {
                em.remove(entity);
                return;
            }
            final T toRemove = em.find(type, entity.getId());
            if (toRemove != null) {
                em.remove(toRemove);
            }
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }
}
