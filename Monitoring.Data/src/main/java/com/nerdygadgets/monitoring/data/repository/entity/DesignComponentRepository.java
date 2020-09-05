package com.nerdygadgets.monitoring.data.repository.entity;

import com.nerdygadgets.monitoring.data.entities.DesignComponent;

import javax.persistence.Query;
import java.util.List;

/**
 * DesignComponent implementation of the Repository pattern to get, create/update and delete DesignComponent objects.
 */
public class DesignComponentRepository extends Repository<DesignComponent> implements DesignComponentRepositoryInterface {

    /**
     * Get a list of design components from the database.
     * @return A list with DesignComponent objects.
     */
    public List<DesignComponent> getDesignComponents() {
        Query query = entityManager.createQuery("SELECT dc FROM DesignComponent dc");
        List<DesignComponent> result = query.getResultList();

        return result;
    }
}
