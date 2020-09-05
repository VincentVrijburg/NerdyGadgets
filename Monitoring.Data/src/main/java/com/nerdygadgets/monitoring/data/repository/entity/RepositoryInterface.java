package com.nerdygadgets.monitoring.data.repository.entity;

/**
 * Default Repository interface to get, create/update and delete Entity objects.
 * @param <E> A generic Entity class.
 */
public interface RepositoryInterface<E> extends AutoCloseable {
    E findById(Object id);
    void persist(E entity);
    void remove(E entity);
}
