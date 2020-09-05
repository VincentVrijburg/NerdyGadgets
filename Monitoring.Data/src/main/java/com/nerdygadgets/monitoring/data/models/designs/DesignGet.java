package com.nerdygadgets.monitoring.data.models.designs;

import com.nerdygadgets.monitoring.data.models.components.ComponentGet;
import com.nerdygadgets.monitoring.data.models.users.UserGet;
import java.util.List;

public class DesignGet {
    public String id;
    public UserGet user;
    public List<ComponentGet> components;
}
