package controllers;

import com.nerdygadgets.monitoring.data.entities.*;
import com.nerdygadgets.monitoring.data.models.designs.DesignCreate;
import com.nerdygadgets.monitoring.data.models.designs.DesignGet;
import com.nerdygadgets.monitoring.data.models.designs.DesignList;
import com.nerdygadgets.monitoring.data.models.designs.DesignUpdate;
import com.nerdygadgets.monitoring.data.repository.entity.*;
import com.nerdygadgets.monitoring.data.utils.Utils;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Handler;

import java.util.*;

public class DesignController {

    /**
     * Method to fetch all the designs and return them in an array, in JSON format.
     */
    public static Handler fetchAll = ctx -> {
        try (DesignRepositoryInterface designRepository = new DesignRepository()) {
            DesignList designList = new DesignList();
            designList.designs = new ArrayList<>();

            // Get all the designs from the database.
            Iterable<Design> allDesigns = designRepository.getDesigns();
            for (Design design: allDesigns) {
                // Iterate through each design and format it to a JSON compatible Design model.
                DesignGet designGet = Utils.EntityUtils.formatDesignGet(design);

                designList.designs.add(designGet);
            }

            // Return the result as JSON.
            ctx.json(designList);
        }
    };

    /**
     * Method to fetch a design by its ID and return it in JSON format.
     */
    public static Handler fetchById = ctx -> {
        try (DesignRepositoryInterface designRepository = new DesignRepository()) {
            // Get the resource identifier from the request.
            String id = Objects.requireNonNull(ctx.pathParam("id"));
            // Get the design with the given identifier, from the database.
            Design design = designRepository.findById(id);

            // Format the design into a JSON compatible Design model.
            DesignGet designGet = Utils.EntityUtils.formatDesignGet(design);

            // Return the result as JSON.
            ctx.json(designGet);
        }
    };

    /**
     * Method to create a design and return the result in JSON format.
     */
    public static Handler create = ctx -> {
        try (DesignRepositoryInterface designRepository = new DesignRepository()) {
            try (UserRepositoryInterface userRepository = new UserRepository()) {
                try (ComponentRepositoryInterface componentRepository = new ComponentRepository()) {
                    // Deserialize the JSON body from the context into a model class.
                    DesignCreate designCreate = ctx.bodyAsClass(DesignCreate.class);

                    // Check if the mandatory JSON attribute is present in the request context.
                    if (designCreate.componentIds == null || designCreate.componentIds.isEmpty())
                        throw new BadRequestResponse("ComponentIds not provided.");

                    // Create a new Design object.
                    Design design = new Design();

                    // Attach a user to the design if the identifier is present in the request context.
                    if (designCreate.userId != null && !designCreate.userId.trim().isEmpty()) {
                        // Get the user with the given identifier, from the database.
                        User user = userRepository.findById(designCreate.userId);
                        if (user == null) {
                            throw new BadRequestResponse("Invalid UserId provided.");
                        }

                        // Attach the user to the new design.
                        design.setUser(user);
                    }

                    Date dateNow = new Date();

                    // Get all the design components from the database (should be empty at this point; design is new)
                    Set<DesignComponent> designComponents = design.getDesignComponents();
                    // Iterate through each component id in the request context.
                    for (String componentId: designCreate.componentIds) {
                        // Get the component with the loop identifier, from the database.
                        Component component = componentRepository.findById(componentId);
                        if (component == null) {
                            throw new BadRequestResponse("An invalid ComponentId provided.");
                        }

                        // Create a new DesignComponent object.
                        DesignComponent designComponent = new DesignComponent();

                        // Set the different attribute values for the new DesignComponent object.
                        designComponent.setDesign(design);
                        designComponent.setComponent(component);
                        designComponent.setUpdated(dateNow);
                        designComponent.setCreated(dateNow);

                        designComponents.add(designComponent);
                    }

                    // Set the DesignComponents of the design to the new list populated list.
                    design.setDesignComponents(designComponents);

                    // Set the different attribute values for the new Design object.
                    design.setUpdated(dateNow);
                    design.setCreated(dateNow);

                    // Save the new design into the database.
                    designRepository.persist(design);

                    // Format the design into a JSON compatible Design class.
                    DesignGet designGet = Utils.EntityUtils.formatDesignGet(design);

                    // Return the result as JSON.
                    ctx.json(designGet);
                    // Set the status code (default: 200 OK)
                    ctx.status(201);
                }
            }
        }
    };

    /**
     * Method to update a design and return the result in JSON format.
     */
    public static Handler updateById = ctx -> {
        try (DesignRepositoryInterface designRepository = new DesignRepository()) {
            try (UserRepositoryInterface userRepository = new UserRepository()) {
                try (ComponentRepositoryInterface componentRepository = new ComponentRepository()) {
                    // Deserialize the JSON body from the context into a model class.
                    DesignUpdate designUpdate = ctx.bodyAsClass(DesignUpdate.class);

                    // Get the resource identifier from the request.
                    String id = Objects.requireNonNull(ctx.pathParam("id"));
                    // Get the design with the given identifier, from the database.
                    Design design = designRepository.findById(id);

                    // Update the user if the attribute is present in the request context, otherwise disconnect.
                    if (designUpdate.userId != null && !designUpdate.userId.trim().isEmpty()) {
                        User user = userRepository.findById(designUpdate.userId);
                        if (user == null) {
                            throw new BadRequestResponse("Invalid UserId provided.");
                        }

                        design.setUser(user);
                    } else {
                        design.setUser(null);
                    }

                    Date dateNow = new Date();

                    // Re-populate the list of attached components, via the DesignComponent (linking) table.
                    if (designUpdate.componentIds != null && !designUpdate.componentIds.isEmpty()) {
                        // Get all the designComponents from the database.
                        Set<DesignComponent> designComponents = design.getDesignComponents();
                        designComponents.clear();

                        // Iterate through each designComponent and format it to a JSON compatible DesignComponent model.
                        for (String componentId: designUpdate.componentIds) {
                            // Get the component with the loop identifier, from the database.
                            Component component = componentRepository.findById(componentId);
                            if (component == null) {
                                throw new BadRequestResponse("An invalid ComponentId provided.");
                            }

                            // Create a new DesignComponent object and assign values to the attributes.
                            DesignComponent designComponent = new DesignComponent();
                            designComponent.setDesign(design);
                            designComponent.setComponent(component);
                            designComponent.setUpdated(dateNow);
                            designComponent.setCreated(dateNow);

                            designComponents.add(designComponent);
                        }

                        // Attach the new list of designComponents to the design.
                        design.setDesignComponents(designComponents);
                    }

                    design.setUpdated(dateNow);

                    // Save the updated design into the database.
                    designRepository.persist(design);
                    // Format the design into a JSON compatible Design class.
                    DesignGet designGet = Utils.EntityUtils.formatDesignGet(design);

                    // Return the result as JSON.
                    ctx.json(designGet);
                }
            }
        }
    };

    /**
     * Method to delete a design by its ID and return a 204 HTTP confirmation result.
     */
    public static Handler deleteById = ctx -> {
        try (DesignRepositoryInterface designRepository = new DesignRepository()) {
            // Get the resource identifier from the request.
            String id = Objects.requireNonNull(ctx.pathParam("id"));
            // Get the design with the given identifier, from the database.
            Design design = designRepository.findById(id);

            // Remove the design from the database.
            designRepository.remove(design);

            // Return a confirmation response code.
            ctx.status(204);
        }
    };
}
