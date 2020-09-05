package com.nerdygadgets.monitoring.data.repository.entity;

import com.nerdygadgets.monitoring.data.entities.Log;

import javax.persistence.Query;
import java.util.List;

/**
 * Log implementation of the Repository pattern to get, create/update and delete Log objects.
 */
public class LogRepository extends Repository<Log> implements LogRepositoryInterface {

    /**
     * Get a list of logs from the database.
     * @return A list with Log objects.
     */
    public List<Log> getLogs() {
        Query query = entityManager.createQuery("SELECT l FROM Log l");
        List<Log> result = query.getResultList();

        return result;
    }
}
