package com.nerdygadgets.monitoring.data.repository.entity;

import com.nerdygadgets.monitoring.data.DataAccess;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.lang.reflect.ParameterizedType;

/**
 * Default implementation of the Repository pattern to get, create/update and delete Entity objects.
 * @param <E> A generic Entity class.
 */
public abstract class Repository<E> implements RepositoryInterface<E> {

    protected Class<E> entityClass;
    protected EntityManager entityManager;

    public Repository() {
        this.entityManager = DataAccess.EntityManager.entityManagerFactory.createEntityManager();

        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[0];
    }

    /**
     * Find an Entity object by its identifier.
     * @param id The identifier of the wanted Entity object.
     * @return The entity object or NULL if it couldn't be found.
     */
    public E findById(Object id) {
        return entityManager.find(entityClass, id);
    }

    /**
     * Persist (create and update) an Entity object.
     * @param entity The Entity object to persist.
     */
    @Transactional
    public void persist(E entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    /**
     * Remove (delete) an Entity object.
     * @param entity The Entity object to remove.
     */
    @Transactional
    public void remove(E entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    public void close() {
        this.entityManager.close();
    }
}
