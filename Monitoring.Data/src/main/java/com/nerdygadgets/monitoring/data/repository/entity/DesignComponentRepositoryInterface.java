package com.nerdygadgets.monitoring.data.repository.entity;

import com.nerdygadgets.monitoring.data.entities.DesignComponent;
import java.util.List;

/**
 * DesignComponent Repository interface to get, create/update and delete DesignComponent objects.
 */
public interface DesignComponentRepositoryInterface extends RepositoryInterface<DesignComponent> {
    public List<DesignComponent> getDesignComponents();
}
