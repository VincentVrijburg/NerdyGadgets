package com.nerdygadgets.monitoring.data.repository.entity;

import com.nerdygadgets.monitoring.data.entities.Component;
import com.nerdygadgets.monitoring.data.enums.ComponentName;
import com.nerdygadgets.monitoring.data.enums.ComponentType;

import java.util.List;

/**
 * Component Repository interface to get, create/update and delete Component objects.
 */
public interface ComponentRepositoryInterface extends RepositoryInterface<Component> {
    public Component getByName(ComponentName componentName);
    public List<Component> getByType(ComponentType componentType);
    public List<Component> getComponents();
}
