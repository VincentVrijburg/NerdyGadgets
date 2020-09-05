package com.nerdygadgets.monitoring.app.panels;

import javax.swing.*;
import java.awt.*;

public class MonitorPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public MonitorPanel() {
        initializePanel();
    }

    public void initializePanel() {
        FlowLayout flowLayout = new FlowLayout(0, 10, 10);
        setLayout(flowLayout);
        setPreferredSize(null);
    }

    public void updatePanel() {
        this.revalidate();
        this.repaint();
    }
}
