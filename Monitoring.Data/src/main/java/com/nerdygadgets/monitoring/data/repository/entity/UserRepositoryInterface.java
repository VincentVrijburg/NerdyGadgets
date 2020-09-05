package com.nerdygadgets.monitoring.data.repository.entity;

import com.nerdygadgets.monitoring.data.entities.User;
import java.util.List;

/**
 * User Repository interface to get, create/update and delete User objects.
 */
public interface UserRepositoryInterface extends RepositoryInterface<User> {
    public User findByUsername(String username);
    public List<User> getUsers();
}
