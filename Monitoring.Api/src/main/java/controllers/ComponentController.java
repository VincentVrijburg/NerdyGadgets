package controllers;

import com.nerdygadgets.monitoring.data.entities.Component;
import com.nerdygadgets.monitoring.data.models.components.ComponentGet;
import com.nerdygadgets.monitoring.data.models.components.ComponentList;
import com.nerdygadgets.monitoring.data.repository.entity.ComponentRepository;
import com.nerdygadgets.monitoring.data.repository.entity.ComponentRepositoryInterface;
import com.nerdygadgets.monitoring.data.utils.Utils;
import io.javalin.http.Handler;

import java.util.ArrayList;
import java.util.Objects;

public class ComponentController {

    /**
     * Method to fetch all the components and return them in an array, in JSON format.
     */
    public static Handler fetchAll = ctx -> {
        try (ComponentRepositoryInterface componentRepository = new ComponentRepository()) {
            ComponentList componentList = new ComponentList();
            componentList.components = new ArrayList<>();

            // Get all the components from the database.
            Iterable<Component> allComponents = componentRepository.getComponents();
            for (Component component: allComponents) {
                // Iterate through each component and format it to a JSON compatible Component model.
                ComponentGet componentGet = Utils.EntityUtils.formatComponentGet(component);
                componentList.components.add(componentGet);
            }

            // Return the result as JSON.
            ctx.json(componentList);
        }
    };

    /**
     * Method to fetch a component by its ID and return it in JSON format.
     */
    public static Handler fetchById = ctx -> {
        try (ComponentRepositoryInterface componentRepository = new ComponentRepository()) {
            // Get the resource identifier from the request.
            String id = Objects.requireNonNull(ctx.pathParam("id"));
            // Get the component with the given identifier, from the database.
            Component component = componentRepository.findById(id);

            // Format the component into a JSON compatible Component model.
            ComponentGet componentGet = Utils.EntityUtils.formatComponentGet(component);

            // Return the result as JSON.
            ctx.json(componentGet);
        }
    };
}
