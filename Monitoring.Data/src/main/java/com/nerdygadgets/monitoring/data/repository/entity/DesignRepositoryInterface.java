package com.nerdygadgets.monitoring.data.repository.entity;

import com.nerdygadgets.monitoring.data.entities.Design;

import java.util.List;

/**
 * Design Repository interface to get, create/update and delete Design objects.
 */
public interface DesignRepositoryInterface extends RepositoryInterface<Design> {
    public List<Design> getDesigns();
}
