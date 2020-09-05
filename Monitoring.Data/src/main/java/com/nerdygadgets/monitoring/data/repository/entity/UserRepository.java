package com.nerdygadgets.monitoring.data.repository.entity;

import com.nerdygadgets.monitoring.data.entities.User;

import javax.persistence.Query;
import java.util.List;

/**
 * User implementation of the Repository pattern to get, create/update and delete User objects.
 */
public class UserRepository extends Repository<User> implements UserRepositoryInterface {

    public User findByUsername(String username) {
        try {
            Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = ?");
            query.setParameter(0, username);
            User result = (User) query.getSingleResult();

            return result;
        } catch(Exception exc) {
            return null;
        }
    }

    /**
     * Get a list of users from the database.
     * @return A list with User objects.
     */
    public List<User> getUsers() {
        Query query = entityManager.createQuery("SELECT u FROM User u");
        List<User> result = query.getResultList();

        return result;
    }
}
