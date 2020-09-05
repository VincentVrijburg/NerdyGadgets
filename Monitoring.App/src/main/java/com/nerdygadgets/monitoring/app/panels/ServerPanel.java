package com.nerdygadgets.monitoring.app.panels;

import com.nerdygadgets.monitoring.data.entities.Component;
import com.nerdygadgets.monitoring.data.entities.ComponentDetail;
import com.nerdygadgets.monitoring.data.enums.ComponentType;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.text.DecimalFormat;

public class ServerPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static DecimalFormat diskspaceFormat = new DecimalFormat("#.###'Gb'");

    private String name;
    private ComponentDetail componentDetail;

    public ServerPanel(String name, ComponentDetail componentDetail) {
        this.name = name;
        this.componentDetail = componentDetail;
    }

    public void initializePanel(boolean isOnline) {
        // Set the background to light green (default)
        setBackground(new Color(163, 247, 181));

        setPreferredSize(new Dimension(200, 300));
        setMaximumSize(getPreferredSize());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createRigidArea(new Dimension(10, 10)));
        setBorder(new LineBorder(Color.gray, 2));

        JLabel title = new JLabel(this.name);
        title.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        add(title, BorderLayout.CENTER);

        add(Box.createVerticalGlue());

        if (isOnline) {
            setBackground(new Color(163, 247, 181));

            JLabel availability = new JLabel("IP Address: " + this.componentDetail.getIpAddress());
            availability.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
            add(availability, BorderLayout.CENTER);

            JLabel cost = new JLabel("CPU: " + this.componentDetail.getProcessorLoad() + "%");
            cost.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
            add(cost, BorderLayout.CENTER);

            JLabel memoryUsed = new JLabel("Memory used: " + this.componentDetail.getMemoryUsed() + "mb");
            memoryUsed.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
            add(memoryUsed, BorderLayout.CENTER);

            JLabel memoryTotal = new JLabel("Memory total: " + this.componentDetail.getMemoryTotal() + "mb");
            memoryTotal.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
            add(memoryTotal, BorderLayout.CENTER);

            JLabel diskspaceUsed = new JLabel("Diskspace used: " + diskspaceFormat.format(this.componentDetail.getDiskspaceUsed()));
            diskspaceUsed.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
            add(diskspaceUsed, BorderLayout.CENTER);

            JLabel diskspaceTotal = new JLabel("Diskspace total: " + diskspaceFormat.format(this.componentDetail.getDiskspaceTotal()));
            diskspaceTotal.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
            add(diskspaceTotal, BorderLayout.CENTER);
        } else {
            setBackground(new Color(218, 62, 82));

            JLabel offline = new JLabel("This server is offline!");
            offline.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
            add(offline, BorderLayout.CENTER);
        }

        add(Box.createVerticalStrut(10));
    }
}
