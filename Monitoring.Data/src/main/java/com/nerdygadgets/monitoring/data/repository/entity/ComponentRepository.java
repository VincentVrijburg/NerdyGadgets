package com.nerdygadgets.monitoring.data.repository.entity;

import com.nerdygadgets.monitoring.data.entities.Component;
import com.nerdygadgets.monitoring.data.enums.ComponentName;
import com.nerdygadgets.monitoring.data.enums.ComponentType;

import javax.persistence.Query;
import java.util.List;

/**
 * Component implementation of the Repository pattern to get, create/update and delete Component objects.
 */
public class ComponentRepository extends Repository<Component> implements ComponentRepositoryInterface {

    public Component getByName(ComponentName componentName) {
        Query query = entityManager.createQuery("SELECT c FROM Component c WHERE name = ?");
        query.setParameter(0, componentName);
        Component result = (Component)query.getSingleResult();

        return result;
    }

    public List<Component> getByType(ComponentType componentType) {
        Query query = entityManager.createQuery("SELECT c FROM Component c WHERE type = ?");
        query.setParameter(0, componentType);
        List<Component> result = query.getResultList();

        return result;
    }

    /**
     * Get a list of components from the database.
     * @return A list with Component objects.
     */
    public List<Component> getComponents() {
        Query query = entityManager.createQuery("SELECT c FROM Component c");
        List<Component> result = query.getResultList();

        return result;
    }
}
