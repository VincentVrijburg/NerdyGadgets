package com.nerdygadgets.monitoring.data.models.components.details;

import com.nerdygadgets.monitoring.data.models.components.ComponentGet;

import java.util.Date;

public class ComponentDetailGet {
    public String id;
    public ComponentGet component;
    public String name;
    public String ipAddress;
    public Date availableSince;
    public double processorLoad;
    public double memoryUsed;
    public double memoryTotal;
    public double diskspaceUsed;
    public double diskspaceTotal;
    public Date updated;
    public Date created;
}
