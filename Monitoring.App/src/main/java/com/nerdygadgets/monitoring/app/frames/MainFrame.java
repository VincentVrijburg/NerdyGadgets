package com.nerdygadgets.monitoring.app.frames;

import com.nerdygadgets.monitoring.app.panels.DesignerPanel;
import com.nerdygadgets.monitoring.app.panels.MonitoringPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public static Container mainContainer;
    public static DesignerPanel designerPanel;
    public static MonitoringPanel monitoringPanel;

    public MainFrame(String setTitle)
    {
        // Initializing the container and frames.
        mainContainer = getContentPane();
        designerPanel = new DesignerPanel();
        monitoringPanel = new MonitoringPanel();

        // Setting numerous properties for the current main frame.
        setTitle(setTitle);
        setSize(1280,720);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Method to update the container and frames.
    public static void update() {
        designerPanel.revalidate();
        designerPanel.repaint();

        monitoringPanel.revalidate();
        monitoringPanel.repaint();

        mainContainer.revalidate();
        mainContainer.repaint();
    }

    // Method to set the design frame visible.
    public static void setDesignVisible() {
        mainContainer.removeAll();
        designerPanel.addToMainContainer();
        mainContainer.add(designerPanel);
        update();
    }

    // Method to set the monitoring frame visible.
    public static void setMonitoringVisible() {
        mainContainer.removeAll();
        mainContainer.add(monitoringPanel);
        update();
    }
}
