package com.nerdygadgets.monitoring.data.repository.entity;

import com.nerdygadgets.monitoring.data.entities.ComponentDetail;

import java.util.List;

/**
 * ComponentDetail Repository interface to get, create/update and delete ComponentDetail objects.
 */
public interface ComponentDetailRepositoryInterface extends RepositoryInterface<ComponentDetail> {
    public ComponentDetail getLatestByName(String name);
    public List<ComponentDetail> getComponentDetails();
}
