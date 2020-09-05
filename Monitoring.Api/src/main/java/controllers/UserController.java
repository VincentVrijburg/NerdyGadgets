package controllers;

import com.nerdygadgets.monitoring.data.entities.User;
import com.nerdygadgets.monitoring.data.repository.entity.UserRepository;
import com.nerdygadgets.monitoring.data.repository.entity.UserRepositoryInterface;
import com.nerdygadgets.monitoring.data.utils.Utils;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Handler;
import com.nerdygadgets.monitoring.data.models.users.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class UserController {

    /**
     * Method to fetch all the users and return them in an array, in JSON format.
     */
    public static Handler fetchAll = ctx -> {
        try (UserRepositoryInterface userRepository = new UserRepository()) {
            UserList userList = new UserList();
            userList.users = new ArrayList<>();

            // Get all the users from the database.
            Iterable<User> allUsers = userRepository.getUsers();
            for (User user: allUsers) {
                // Iterate through each user and format it to a JSON compatible User model.
                UserGet userGet = Utils.EntityUtils.formatUserGet(user);
                userList.users.add(userGet);
            }

            // Return the result as JSON.
            ctx.json(userList);
        }
    };

    /**
     * Method to fetch a user by its ID and return it in JSON format.
     */
    public static Handler fetchById = ctx -> {
        try (UserRepositoryInterface userRepository = new UserRepository()) {
            // Get the resource identifier from the request.
            String id = Objects.requireNonNull(ctx.pathParam("id"));
            // Get the user with the given identifier, from the database.
            User user = userRepository.findById(id);

            // Format the user into a JSON compatible User model.
            UserGet userGet = Utils.EntityUtils.formatUserGet(user);

            // Return the result as JSON.
            ctx.json(userGet);
        }
    };

    /**
     * Method to create a user and return the result in JSON format.
     */
    public static Handler create = ctx -> {
        try (UserRepositoryInterface userRepository = new UserRepository()) {
            // Deserialize the JSON body from the context into a model class.
            UserCreate userCreate = ctx.bodyAsClass(UserCreate.class);

            // Check if the mandatory JSON attribute is present in the request context.
            if (userCreate.username == null || userCreate.username.trim().isEmpty())
                throw new BadRequestResponse("Username not provided.");

            // Check if the mandatory JSON attribute is present in the request context.
            if (userCreate.password == null || userCreate.password.trim().isEmpty())
                throw new BadRequestResponse("Password not provided.");

            // Create a new User object.
            User user = new User();

            // Set the different attribute values for the new user object.
            user.setUsername(userCreate.username);
            user.setPassword(userCreate.password);
            user.setActive(userCreate.isActive);
            Date dateNow = new Date();
            user.setUpdated(dateNow);
            user.setCreated(dateNow);

            // Save the new user into the database.
            userRepository.persist(user);

            // Format the user into a JSON compatible User class.
            UserGet userGet = Utils.EntityUtils.formatUserGet(user);

            // Return the result as JSON.
            ctx.json(userGet);
            // Set the status code (default: 200 OK)
            ctx.status(201);
        }
    };

    /**
     * Method to update a user and return the result in JSON format.
     */
    public static Handler updateById = ctx -> {
        try (UserRepositoryInterface userRepository = new UserRepository()) {
            // Deserialize the JSON body from the context into a model class.
            UserUpdate userUpdate = ctx.bodyAsClass(UserUpdate.class);

            // Get the resource identifier from the request.
            String id = Objects.requireNonNull(ctx.pathParam("id"));
            // Get the user with the given identifier, from the database.
            User user = userRepository.findById(id);

            // Update the attribute value if the attribute is present in the request context.
            if (userUpdate.username != null && !userUpdate.username.trim().isEmpty())
                user.setUsername(userUpdate.username);

            // Update the attribute value if the attribute is present in the request context.
            if (userUpdate.password != null && !userUpdate.password.trim().isEmpty())
                user.setPassword(userUpdate.password);

            // Update the attribute value if the attribute is present in the request context.
            if (userUpdate.isActive != null)
                user.setActive(userUpdate.isActive);

            // Update the updated datetime.
            Date dateNow = new Date();
            user.setUpdated(dateNow);

            // Save the updated user into the database.
            userRepository.persist(user);
            // Format the user into a JSON compatible User class.
            UserGet userGet = Utils.EntityUtils.formatUserGet(user);

            // Return the result as JSON.
            ctx.json(userGet);
        }
    };

    /**
     * Method to delete a user by its ID and return a 204 HTTP confirmation result.
     */
    public static Handler deleteById = ctx -> {
        try (UserRepositoryInterface userRepository = new UserRepository();) {
            // Get the resource identifier from the request.
            String id = Objects.requireNonNull(ctx.pathParam("id"));
            // Get the user with the given identifier, from the database.
            User user = userRepository.findById(id);

            // Remove the user from the database.
            userRepository.remove(user);

            // Return a confirmation response code.
            ctx.status(204);
        }
    };
}
