package com.nerdygadgets.monitoring.app.panels;

import com.nerdygadgets.monitoring.app.frames.MainFrame;

import javax.swing.*;
import java.awt.*;

public class DesignPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public DesignPanel() {
        initializePanel();
    }

    public void initializePanel() {
        FlowLayout flowLayout = new FlowLayout(0, 8, 8);
        setLayout(flowLayout);
        setPreferredSize(MainFrame.mainContainer.getPreferredSize());
    }

    public void updatePanel() {
        this.revalidate();
        this.repaint();
    }
}
