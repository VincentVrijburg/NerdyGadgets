package com.nerdygadgets.monitoring.data.models.components.details;

import java.util.Date;

public class ComponentDetailCreate {
    public String componentId;
    public String name;
    public String ipAddress;
    public Date availableSince;
    public Double processorLoad;
    public Double memoryUsed;
    public Double memoryTotal;
    public Double diskspaceUsed;
    public Double diskspaceTotal;
}
