package controllers;

import com.nerdygadgets.monitoring.data.entities.Component;
import com.nerdygadgets.monitoring.data.entities.ComponentDetail;
import com.nerdygadgets.monitoring.data.models.components.details.ComponentDetailCreate;
import com.nerdygadgets.monitoring.data.models.components.details.ComponentDetailGet;
import com.nerdygadgets.monitoring.data.models.components.details.ComponentDetailList;
import com.nerdygadgets.monitoring.data.models.components.details.ComponentDetailUpdate;
import com.nerdygadgets.monitoring.data.repository.entity.*;
import com.nerdygadgets.monitoring.data.utils.Utils;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class ComponentDetailController {

    /**
     * Method to fetch all the component details and return them in an array, in JSON format.
     */
    public static Handler fetchAll = ctx -> {
        try (ComponentDetailRepositoryInterface componentDetailRepository = new ComponentDetailRepository()) {
            ComponentDetailList componentDetailList = new ComponentDetailList();
            componentDetailList.componentDetails = new ArrayList<>();

            // Get all the componentDetails from the database.
            Iterable<ComponentDetail> componentDetails = componentDetailRepository.getComponentDetails();
            for (ComponentDetail componentDetail: componentDetails) {
                // Iterate through each componentDetail and format it to a JSON compatible ComponentDetail model.
                ComponentDetailGet componentDetailGet = Utils.EntityUtils.formatComponentDetailGet(componentDetail);
                componentDetailGet.component = Utils.EntityUtils.formatComponentGet(componentDetail.getComponent());
                componentDetailList.componentDetails.add(componentDetailGet);
            }

            // Return the result as JSON.
            ctx.json(componentDetailList);
        }
    };

    /**
     * Method to fetch a component detail by its ID and return it in JSON format.
     */
    public static Handler fetchById = ctx -> {
        try (ComponentDetailRepositoryInterface componentDetailRepository = new ComponentDetailRepository()) {
            // Get the resource identifier from the request.
            String id = Objects.requireNonNull(ctx.pathParam("id"));
            // Get the componentDetail with the given identifier, from the database.
            ComponentDetail componentDetail = componentDetailRepository.findById(id);

            // Format the componentDetail into a JSON compatible ComponentDetail model.
            ComponentDetailGet componentDetailGet = Utils.EntityUtils.formatComponentDetailGet(componentDetail);
            componentDetailGet.component = Utils.EntityUtils.formatComponentGet(componentDetail.getComponent());

            // Return the result as JSON.
            ctx.json(componentDetailGet);
        }
    };

    /**
     * Method to create a component detail and return the result in JSON format.
     */
    public static Handler create = ctx -> {
        try (ComponentDetailRepositoryInterface componentDetailRepository = new ComponentDetailRepository()) {
            try (ComponentRepositoryInterface componentRepository = new ComponentRepository()) {
                // Deserialize the JSON body from the context into a model class.
                ComponentDetailCreate componentDetailCreate = ctx.bodyAsClass(ComponentDetailCreate.class);

                // Check if the mandatory JSON attribute is present in the request context.
                if (componentDetailCreate.componentId == null || componentDetailCreate.componentId.trim().isEmpty())
                    throw new BadRequestResponse("ComponentId not provided.");

                // Check if the mandatory JSON attribute is present in the request context.
                if (componentDetailCreate.name == null || componentDetailCreate.name.trim().isEmpty())
                    throw new BadRequestResponse("Name not provided.");

                // Check if the mandatory JSON attribute is present in the request context.
                if (componentDetailCreate.ipAddress == null || componentDetailCreate.ipAddress.trim().isEmpty())
                    throw new BadRequestResponse("IpAddress not provided.");

                // Check if the mandatory JSON attribute is present in the request context.
                if (componentDetailCreate.availableSince == null)
                    throw new BadRequestResponse("AvailableSince date not provided.");

                // Check if the mandatory JSON attribute is present in the request context.
                if (componentDetailCreate.processorLoad == null)
                    throw new BadRequestResponse("ProcessorLoad not provided.");

                // Check if the mandatory JSON attribute is present in the request context.
                if (componentDetailCreate.memoryUsed == null)
                    throw new BadRequestResponse("MemoryUsed not provided.");

                // Check if the mandatory JSON attribute is present in the request context.
                if (componentDetailCreate.memoryTotal == null)
                    throw new BadRequestResponse("MemoryTotal not provided.");

                // Check if the mandatory JSON attribute is present in the request context.
                if (componentDetailCreate.diskspaceUsed == null)
                    throw new BadRequestResponse("DiskspaceUsed not provided.");

                // Check if the mandatory JSON attribute is present in the request context.
                if (componentDetailCreate.diskspaceTotal == null)
                    throw new BadRequestResponse("DiskspaceTotal not provided.");


                Component component = componentRepository.findById(componentDetailCreate.componentId);
                if (component == null) {
                    throw new BadRequestResponse("Invalid ComponentId provided.");
                }

                // Create a new ComponentDetail object.
                ComponentDetail componentDetail = new ComponentDetail();

                // Set the different attribute values for the new componentDetail object.
                componentDetail.setComponent(component);
                componentDetail.setName(componentDetailCreate.name);
                componentDetail.setIpAddress(componentDetailCreate.ipAddress);
                componentDetail.setAvailableSince(componentDetailCreate.availableSince);
                componentDetail.setProcessorLoad(componentDetailCreate.processorLoad);
                componentDetail.setMemoryUsed(componentDetailCreate.memoryUsed);
                componentDetail.setMemoryTotal(componentDetailCreate.memoryTotal);
                componentDetail.setDiskspaceUsed(componentDetailCreate.diskspaceUsed);
                componentDetail.setDiskspaceTotal(componentDetailCreate.diskspaceTotal);
                Date dateNow = new Date();
                componentDetail.setUpdated(dateNow);
                componentDetail.setCreated(dateNow);

                // Save the new componentDetail into the database.
                componentDetailRepository.persist(componentDetail);

                // Format the componentDetail into a JSON compatible ComponentDetail class.
                ComponentDetailGet componentDetailGet = Utils.EntityUtils.formatComponentDetailGet(componentDetail);
                componentDetailGet.component = Utils.EntityUtils.formatComponentGet(componentDetail.getComponent());

                // Return the result as JSON.
                ctx.json(componentDetailGet);

                // Set the status code (default: 200 OK)
                ctx.status(201);
            }
        }
    };

    /**
     * Method to update a component detail and return the result in JSON format.
     */
    public static Handler updateById = ctx -> {
        try (ComponentDetailRepositoryInterface componentDetailRepository = new ComponentDetailRepository()) {
            try (ComponentRepositoryInterface componentRepository = new ComponentRepository()) {
                // Deserialize the JSON body from the context into a model class.
                ComponentDetailUpdate componentDetailUpdate = ctx.bodyAsClass(ComponentDetailUpdate.class);

                // Get the resource identifier from the request.
                String id = Objects.requireNonNull(ctx.pathParam("id"));
                // Get the componentDetail with the given identifier, from the database.
                ComponentDetail componentDetail = componentDetailRepository.findById(id);

                // Update the attribute value if the attribute is present in the request context.
                if (componentDetailUpdate.componentId != null && !componentDetailUpdate.componentId.trim().isEmpty()) {
                    Component component = componentRepository.findById(componentDetailUpdate.componentId);
                    if (component == null) {
                        throw new BadRequestResponse("Invalid ComponentId provided.");
                    }

                    componentDetail.setComponent(component);
                }

                // Update the attribute value if the attribute is present in the request context.
                if (componentDetailUpdate.name != null && !componentDetailUpdate.name.trim().isEmpty())
                    componentDetail.setName(componentDetailUpdate.name);

                // Update the attribute value if the attribute is present in the request context.
                if (componentDetailUpdate.ipAddress != null && !componentDetailUpdate.ipAddress.trim().isEmpty())
                    componentDetail.setIpAddress(componentDetailUpdate.ipAddress);

                // Update the attribute value if the attribute is present in the request context.
                if (componentDetailUpdate.availableSince != null)
                    componentDetail.setAvailableSince(componentDetailUpdate.availableSince);

                // Update the attribute value if the attribute is present in the request context.
                if (componentDetailUpdate.processorLoad != null)
                    componentDetail.setProcessorLoad(componentDetailUpdate.processorLoad);

                // Update the attribute value if the attribute is present in the request context.
                if (componentDetailUpdate.memoryUsed != null)
                    componentDetail.setMemoryUsed(componentDetailUpdate.memoryUsed);

                // Update the attribute value if the attribute is present in the request context.
                if (componentDetailUpdate.memoryTotal != null)
                    componentDetail.setMemoryTotal(componentDetailUpdate.memoryTotal);

                // Update the attribute value if the attribute is present in the request context.
                if (componentDetailUpdate.diskspaceUsed != null)
                    componentDetail.setDiskspaceUsed(componentDetailUpdate.diskspaceUsed);

                // Update the attribute value if the attribute is present in the request context.
                if (componentDetailUpdate.diskspaceTotal != null)
                    componentDetail.setDiskspaceTotal(componentDetailUpdate.diskspaceTotal);

                // Update the updated datetime.
                Date dateNow = new Date();
                componentDetail.setUpdated(dateNow);

                // Save the updated componentDetail into the database.
                componentDetailRepository.persist(componentDetail);
                // Format the componentDetail into a JSON compatible ComponentDetail class.
                ComponentDetailGet componentDetailGet = Utils.EntityUtils.formatComponentDetailGet(componentDetail);
                componentDetailGet.component = Utils.EntityUtils.formatComponentGet(componentDetail.getComponent());

                // Return the result as JSON.
                ctx.json(componentDetailGet);
            }
        }
    };

    /**
     * Method to delete a component detail by its ID and return a 204 HTTP confirmation result.
     */
    public static Handler deleteById = ctx -> {
        try (ComponentDetailRepositoryInterface componentDetailRepository = new ComponentDetailRepository()) {
            // Get the resource identifier from the request.
            String id = Objects.requireNonNull(ctx.pathParam("id"));
            // Get the componentDetail with the given identifier, from the database.
            ComponentDetail componentDetail = componentDetailRepository.findById(id);

            // Remove the user from the database.
            componentDetailRepository.remove(componentDetail);

            // Return a confirmation response code.
            ctx.status(204);
        }
    };
}
