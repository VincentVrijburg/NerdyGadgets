import com.fasterxml.jackson.databind.ObjectMapper;
import com.nerdygadgets.monitoring.data.models.components.details.ComponentDetailCreate;
import com.nerdygadgets.monitoring.data.utils.Utils;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TimerTask;

public class MonitoringTask extends TimerTask {

    private static String POST_URL;
    private static String COMPONENT_ID;
    private static String IP_ADDRESS;
    private static String NAME;

    public MonitoringTask(String endpoint, String componentId, String ipAddress, String name){
        this.POST_URL = endpoint + "/componentdetails";
        this.COMPONENT_ID = componentId;
        this.IP_ADDRESS = ipAddress;
        this.NAME = name;
    }

    @Override
    public void run() {
        System.out.println("Hi see you after 10 seconds");
        postComponentDetailsLog();
    }

    public void postComponentDetailsLog() {
        try {
            ComponentDetailCreate componentDetailPost = formatComponentDetailCreate();

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(componentDetailPost);

            // Post component monitoring details.
            postData(new URL(POST_URL), json);

        } catch (Exception exc) {
            System.out.println(exc.getMessage());
        }
    }

    private ComponentDetailCreate formatComponentDetailCreate() throws Exception {
        ComponentDetailCreate componentDetailPost = new ComponentDetailCreate();
        componentDetailPost.componentId = COMPONENT_ID;
        componentDetailPost.ipAddress = IP_ADDRESS;
        componentDetailPost.name = NAME;
        componentDetailPost.availableSince = Utils.MonitoringUtils.getAvailableSince();
        componentDetailPost.processorLoad = Utils.MonitoringUtils.getSystemCpuLoad();
        componentDetailPost.memoryUsed = Utils.MonitoringUtils.getUsedMemory();
        componentDetailPost.memoryTotal = Utils.MonitoringUtils.getTotalMemory();
        componentDetailPost.diskspaceUsed = Utils.MonitoringUtils.getUsedDiskspace();
        componentDetailPost.diskspaceTotal = Utils.MonitoringUtils.getTotalDiskspace();

        return componentDetailPost;
    }

    private void postData(URL url, String json) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setFixedLengthStreamingMode(json.length());
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.connect();

        OutputStream os = con.getOutputStream();
        os.write(json.getBytes());
        os.flush();
        os.close();

        con.disconnect();
    }
}
