package com.nerdygadgets.monitoring.data.repository.entity;

import com.nerdygadgets.monitoring.data.entities.ComponentDetail;

import javax.persistence.Query;
import java.util.List;

/**
 * ComponentDetail implementation of the Repository pattern to get, create/update and delete ComponentDetail objects.
 */
public class ComponentDetailRepository extends Repository<ComponentDetail> implements ComponentDetailRepositoryInterface {

    @Override
    public ComponentDetail getLatestByName(String name) {
        Query query = entityManager.createQuery("SELECT cd FROM ComponentDetail cd WHERE name = :name ORDER BY created desc");
        query.setParameter("name", name);
        query.setMaxResults(1);
        ComponentDetail result = (ComponentDetail) query.getSingleResult();

        return result;
    }

    /**
     * Get a list of component details from the database.
     * @return A list with ComponentDetail objects.
     */
    public List<ComponentDetail> getComponentDetails() {
        Query query = entityManager.createQuery("SELECT cd FROM ComponentDetail cd");
        List<ComponentDetail> result = query.getResultList();

        return result;
    }
}
