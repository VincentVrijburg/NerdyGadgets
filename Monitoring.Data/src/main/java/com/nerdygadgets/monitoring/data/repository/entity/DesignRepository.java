package com.nerdygadgets.monitoring.data.repository.entity;

import com.nerdygadgets.monitoring.data.entities.Design;

import javax.persistence.Query;
import java.util.List;

/**
 * Design implementation of the Repository pattern to get, create/update and delete Design objects.
 */
public class DesignRepository extends Repository<Design> implements DesignRepositoryInterface {

    /**
     * Get a list of designs from the database.
     * @return A list with Design objects.
     */
    public List<Design> getDesigns() {
        Query query = entityManager.createQuery("SELECT d FROM Design d");
        List<Design> result = query.getResultList();

        return result;
    }
}
