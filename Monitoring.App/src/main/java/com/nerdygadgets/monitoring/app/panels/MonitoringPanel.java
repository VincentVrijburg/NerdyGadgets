package com.nerdygadgets.monitoring.app.panels;

import com.nerdygadgets.monitoring.app.panels.MonitorPanel;
import com.nerdygadgets.monitoring.app.tasks.MonitoringTask;

import javax.swing.*;
import java.util.Timer;

public class MonitoringPanel extends JPanel {

    public static MonitorPanel monitorPanel;

    public MonitoringPanel() {
        // Initializing the monitoring panel and adding it to the mainframe container.
        monitorPanel = new MonitorPanel();
        add(monitorPanel);

        // Setting up the monitoring task, to retrieve new data for the monitoring window.
        java.util.Timer t = new Timer();
        MonitoringTask monitoringTask = new MonitoringTask();

        // This task is scheduled to run every 10 seconds
        t.scheduleAtFixedRate(monitoringTask, 0, 10000);

        setVisible(true);
    }
}
