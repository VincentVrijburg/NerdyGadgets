import com.nerdygadgets.monitoring.data.PropertyReader;

import java.util.Timer;

public class Main {

    public static void main(String[] args) {
        try {
            Timer t = new Timer();

            PropertyReader propertyReader = new PropertyReader();
            String endpoint = propertyReader.getPropertyValue("endpoint");
            String componentId = propertyReader.getPropertyValue("componentId");
            String ipAddress = propertyReader.getPropertyValue("ipAddress");
            String name = propertyReader.getPropertyValue("name");

            MonitoringTask monitoringTask = new MonitoringTask(endpoint, componentId, ipAddress, name);

            // This task is scheduled to run every 10 seconds
            t.scheduleAtFixedRate(monitoringTask, 0, 10000);

        } catch (Exception exc) {
            System.out.println(exc.getMessage());
        }
    }
}