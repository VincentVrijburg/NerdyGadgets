import controllers.*;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        // Create the API and start it with a designated port value.
        Javalin app = Javalin.create().start(8080);

        // Assign paths to corresponding methods in the UserController.
        app.get("/users", UserController.fetchAll);
        app.get("/users/:id", UserController.fetchById);
        app.post("/users", UserController.create);
        app.put("/users/:id", UserController.updateById);
        app.delete("/users/:id", UserController.deleteById);

        // Assign paths to corresponding methods in the DesignController.
        app.get("/designs", DesignController.fetchAll);
        app.get("/designs/:id", DesignController.fetchById);
        app.post("/designs", DesignController.create);
        app.put("/designs/:id", DesignController.updateById);
        app.delete("/designs/:id", DesignController.deleteById);

        // Assign paths to corresponding methods in the ComponentController.
        app.get("/components", ComponentController.fetchAll);
        app.get("/components/:id", ComponentController.fetchById);

        // Assign paths to corresponding methods in the ComponentDetailController.
        app.get("/componentdetails", ComponentDetailController.fetchAll);
        app.get("/componentdetails/:id", ComponentDetailController.fetchById);
        app.post("/componentdetails", ComponentDetailController.create);
        app.put("/componentdetails/:id", ComponentDetailController.updateById);
        app.delete("/componentdetails/:id", ComponentDetailController.deleteById);

        // Assign paths to corresponding methods in the LogController.
        app.get("/logs", LogController.fetchAll);
        app.get("/logs/:id", LogController.fetchById);
        app.post("/logs", LogController.create);
        app.put("/logs/:id", LogController.updateById);
        app.delete("/logs/:id", LogController.deleteById);
    }
}