package com.nerdygadgets.monitoring.data.models.logs;

import com.nerdygadgets.monitoring.data.models.components.details.ComponentDetailGet;

import java.util.Date;

public class LogGet {
    public String id;
    public ComponentDetailGet componentDetail;
    public String type;
    public String error;
    public Boolean isResolved;
    public Date updated;
    public Date created;
}
