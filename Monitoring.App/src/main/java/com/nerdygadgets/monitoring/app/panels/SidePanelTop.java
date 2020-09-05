package com.nerdygadgets.monitoring.app.panels;

import com.nerdygadgets.monitoring.app.listeners.DesignCreateActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SidePanelTop extends JPanel {
    private static final long serialVersionUID = 1L;

    public JTextField percentageField;

    public SidePanelTop() {
        initializePanel();
    }

    public void initializePanel() {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new GridLayout(5, 1));
        add(Box.createRigidArea(new Dimension(10, 0)));
        setBackground(Color.LIGHT_GRAY);
        setMaximumSize(new Dimension(DesignerPanel.rightPanel.getPreferredSize().width, 100));

        add(new JLabel("Generate design", SwingConstants.CENTER));

        this.percentageField = new JTextField();
        this.percentageField.setPreferredSize(new Dimension(50, 24));
        add(this.percentageField);

        JButton generateBtn = new JButton("Generate");
        generateBtn.addActionListener(new DesignCreateActionListener(this));
        add(generateBtn);
    }
}
