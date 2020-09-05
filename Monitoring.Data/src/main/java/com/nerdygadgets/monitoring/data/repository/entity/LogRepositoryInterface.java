package com.nerdygadgets.monitoring.data.repository.entity;

import com.nerdygadgets.monitoring.data.entities.Log;

import java.util.List;

/**
 * Log Repository interface to get, create/update and delete Log objects.
 */
public interface LogRepositoryInterface extends RepositoryInterface<Log> {
    public List<Log> getLogs();
}
