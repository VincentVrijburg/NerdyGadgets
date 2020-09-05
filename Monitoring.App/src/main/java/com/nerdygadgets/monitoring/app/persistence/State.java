package com.nerdygadgets.monitoring.app.persistence;

import com.nerdygadgets.monitoring.data.entities.User;
import com.nerdygadgets.monitoring.app.panels.ComponentPanel;
import java.util.ArrayList;

public class State {

    public static class LoginState {
        public static boolean isLoggedIn = false;

        public static User user;
    }

    public static class DesignState {
        public static ArrayList<ComponentPanel> componentPanelList = new ArrayList<>();
    }
}
