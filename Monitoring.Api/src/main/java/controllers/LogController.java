package controllers;

import com.nerdygadgets.monitoring.data.entities.ComponentDetail;
import com.nerdygadgets.monitoring.data.entities.Log;
import com.nerdygadgets.monitoring.data.enums.LogType;
import com.nerdygadgets.monitoring.data.models.logs.*;
import com.nerdygadgets.monitoring.data.repository.entity.*;
import com.nerdygadgets.monitoring.data.utils.Utils;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Handler;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class LogController {

    /**
     * Method to fetch all the logs and return them in an array, in JSON format.
     */
    public static Handler fetchAll = ctx -> {
        try (LogRepositoryInterface logRepository = new LogRepository()) {
            LogList logList = new LogList();
            logList.logs = new ArrayList<>();

            // Get all the logs from the database.
            Iterable<Log> allLogs = logRepository.getLogs();
            for (Log log: allLogs) {
                // Iterate through each user and format it to a JSON compatible Log model.
                LogGet logGet = Utils.EntityUtils.formatLogGet(log);
                logGet.componentDetail = Utils.EntityUtils.formatComponentDetailGet(log.getComponentDetail());
                logGet.componentDetail.component = Utils.EntityUtils.formatComponentGet(log.getComponentDetail().getComponent());

                logList.logs.add(logGet);
            }

            // Return the result as JSON.
            ctx.json(logList);
        }
    };

    /**
     * Method to fetch a log by its ID and return it in JSON format.
     */
    public static Handler fetchById = ctx -> {
        try (LogRepositoryInterface logRepository = new LogRepository()) {
            // Get the resource identifier from the request.
            String id = Objects.requireNonNull(ctx.pathParam("id"));
            // Get the log with the given identifier, from the database.
            Log log = logRepository.findById(id);

            // Format the log into a JSON compatible Log model.
            LogGet logGet = Utils.EntityUtils.formatLogGet(log);
            logGet.componentDetail = Utils.EntityUtils.formatComponentDetailGet(log.getComponentDetail());
            logGet.componentDetail.component = Utils.EntityUtils.formatComponentGet(log.getComponentDetail().getComponent());

            // Return the result as JSON.
            ctx.json(logGet);
        }
    };

    /**
     * Method to create a log and return the result in JSON format.
     */
    public static Handler create = ctx -> {
        try (LogRepositoryInterface logRepository = new LogRepository()) {
            try (ComponentDetailRepositoryInterface componentDetailRepository = new ComponentDetailRepository()) {
                // Deserialize the JSON body from the context into a model class.
                LogCreate logCreate = ctx.bodyAsClass(LogCreate.class);

                // Check if the mandatory JSON attribute is present in the request context.
                if (logCreate.componentDetailId == null || logCreate.componentDetailId.trim().isEmpty())
                    throw new BadRequestResponse("ComponentDetailId not provided.");

                // Check if the mandatory JSON attribute is present in the request context.
                if (logCreate.type == null || logCreate.type.trim().isEmpty())
                    throw new BadRequestResponse("Type not provided.");

                // Check if the mandatory JSON attribute is present in the request context.
                if (logCreate.error == null || logCreate.error.trim().isEmpty())
                    throw new BadRequestResponse("Error not provided.");

                // Check if the given ComponentDetailId is valid and returns a Non-NULL object.
                ComponentDetail componentDetail = componentDetailRepository.findById(logCreate.componentDetailId);
                if (componentDetail == null) {
                    throw new BadRequestResponse("Invalid ComponentDetailId provided.");
                }

                // Create a new Log object.
                Log log = new Log();

                // Set the different attribute values for the new log object.
                log.setComponentDetail(componentDetail);
                log.setType(LogType.valueOf(logCreate.type));
                log.setError(logCreate.error);
                log.setResolved(logCreate.isResolved);
                Date dateNow = new Date();
                log.setUpdated(dateNow);
                log.setCreated(dateNow);

                // Save the new log into the database.
                logRepository.persist(log);

                // Format the log into a JSON compatible Log class.
                LogGet logGet = Utils.EntityUtils.formatLogGet(log);
                logGet.componentDetail = Utils.EntityUtils.formatComponentDetailGet(log.getComponentDetail());
                logGet.componentDetail.component = Utils.EntityUtils.formatComponentGet(log.getComponentDetail().getComponent());

                // Return the result as JSON.
                ctx.json(logGet);
                // Set the status code (default: 200 OK)
                ctx.status(201);
            }
        }
    };

    /**
     * Method to update a log and return the result in JSON format.
     */
    public static Handler updateById = ctx -> {
        try (LogRepositoryInterface logRepository = new LogRepository()) {
            try (ComponentDetailRepositoryInterface componentDetailRepository = new ComponentDetailRepository()) {
                // Deserialize the JSON body from the context into a model class.
                LogUpdate logUpdate = ctx.bodyAsClass(LogUpdate.class);

                // Get the resource identifier from the request.
                String id = Objects.requireNonNull(ctx.pathParam("id"));
                // Get the log with the given identifier, from the database.
                Log log = logRepository.findById(id);

                // Update the attribute value if the attribute is present in the request context.
                if (logUpdate.componentDetailId != null && !logUpdate.componentDetailId.trim().isEmpty())
                    log.setComponentDetail(componentDetailRepository.findById(logUpdate.componentDetailId));

                // Update the attribute value if the attribute is present in the request context.
                if (logUpdate.type != null && !logUpdate.type.trim().isEmpty())
                    log.setType(LogType.valueOf(logUpdate.type));

                // Update the attribute value if the attribute is present in the request context.
                if (logUpdate.error != null && !logUpdate.error.trim().isEmpty())
                    log.setError(logUpdate.error);

                // Update the attribute value if the attribute is present in the request context.
                if (logUpdate.isResolved != null)
                    log.setResolved(logUpdate.isResolved);

                // Update the updated datetime.
                Date dateNow = new Date();
                log.setUpdated(dateNow);

                // Save the updated log into the database.
                logRepository.persist(log);

                // Format the log into a JSON compatible Log class.
                LogGet logGet = Utils.EntityUtils.formatLogGet(log);
                logGet.componentDetail = Utils.EntityUtils.formatComponentDetailGet(log.getComponentDetail());
                logGet.componentDetail.component = Utils.EntityUtils.formatComponentGet(log.getComponentDetail().getComponent());

                // Return the result as JSON.
                ctx.json(logGet);
            }
        }
    };

    /**
     * Method to delete a log by its ID and return a 204 HTTP confirmation result.
     */
    public static Handler deleteById = ctx -> {
        try (LogRepositoryInterface logRepository = new LogRepository()) {
            // Get the resource identifier from the request.
            String id = Objects.requireNonNull(ctx.pathParam("id"));
            // Get the log with the given identifier, from the database.
            Log log = logRepository.findById(id);

            // Remove the log from the database.
            logRepository.remove(log);

            // Return a confirmation response code.
            ctx.status(204);
        }
    };
}
