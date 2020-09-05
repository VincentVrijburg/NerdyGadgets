package com.nerdygadgets.monitoring.app.tasks;

import com.nerdygadgets.monitoring.app.panels.MonitoringPanel;
import com.nerdygadgets.monitoring.data.entities.ComponentDetail;
import com.nerdygadgets.monitoring.data.repository.entity.ComponentDetailRepository;
import com.nerdygadgets.monitoring.app.panels.ServerPanel;

import java.util.Date;
import java.util.TimerTask;

public class MonitoringTask extends TimerTask {

    private ComponentDetailRepository componentDetailRepository = new ComponentDetailRepository();

    public MonitoringTask(){
    }

    @Override
    public void run() {
        updateMonitoringServers();
    }

    public void updateMonitoringServers() {
        try {
            MonitoringPanel.monitorPanel.removeAll();

            Date comparableDate = new Date();
            comparableDate.setTime(comparableDate.getTime() - 30000);

            ComponentDetail databaseOneDetail = componentDetailRepository.getLatestByName("dbserver 1");
            ServerPanel databaseOnePanel = new ServerPanel("Database 1", databaseOneDetail);
            if (databaseOneDetail.getCreated().getTime() < comparableDate.getTime()) {
                // Missing log for 30 seconds or more, so there must be an issue
                databaseOnePanel.initializePanel(false);
            } else {
                databaseOnePanel.initializePanel(true);
            }
            MonitoringPanel.monitorPanel.add(databaseOnePanel);

            ComponentDetail databaseTwoDetail = componentDetailRepository.getLatestByName("dbserver 2");
            ServerPanel databaseTwoPanel = new ServerPanel("Database 2", databaseTwoDetail);
            if (databaseTwoDetail.getCreated().getTime() < comparableDate.getTime()) {
                // Missing log for 30 seconds or more, so there must be an issue
                databaseTwoPanel.initializePanel(false);
            } else {
                databaseTwoPanel.initializePanel(true);
            }
            MonitoringPanel.monitorPanel.add(databaseTwoPanel);

            ComponentDetail webserverOneDetail = componentDetailRepository.getLatestByName("webserver 1");
            ServerPanel webserverOnePanel = new ServerPanel("Webserver 1", webserverOneDetail);
            if (webserverOneDetail.getCreated().getTime() < comparableDate.getTime()) {
                // Missing log for 30 seconds or more, so there must be an issue
                webserverOnePanel.initializePanel(false);
            } else {
                webserverOnePanel.initializePanel(true);
            }
            MonitoringPanel.monitorPanel.add(webserverOnePanel);

            ComponentDetail webserverTwoDetail = componentDetailRepository.getLatestByName("webserver 2");
            ServerPanel webserverTwoPanel = new ServerPanel("Webserver 2", webserverTwoDetail);
            if (webserverTwoDetail.getCreated().getTime() < comparableDate.getTime()) {
                // Missing log for 30 seconds or more, so there must be an issue
                webserverTwoPanel.initializePanel(false);
            } else {
                webserverTwoPanel.initializePanel(true);
            }
            MonitoringPanel.monitorPanel.add(webserverTwoPanel);

            MonitoringPanel.monitorPanel.updatePanel();

        } catch (Exception exc) {
            System.out.println(exc.getMessage());
        }
    }
}
