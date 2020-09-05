package com.nerdygadgets.monitoring.app.panels;

import com.nerdygadgets.monitoring.data.entities.Component;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ComponentPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private Component component;

    public Component getComponent() {
        return this.component;
    }

    public ComponentPanel(Component component) {
        this.component = component;
        initializePanel();
    }

    public void initializePanel() {
        setBackground(Color.white);
        setPreferredSize(new Dimension(150, 150));
        setMaximumSize(getPreferredSize());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createRigidArea(new Dimension(10, 10)));
        setBorder(new LineBorder(Color.gray, 2));

        JLabel title = new JLabel(this.component.getName().toString());
        title.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        add(title, BorderLayout.CENTER);

        JLabel type = new JLabel("(" + this.component.getType().toString() + ")");
        type.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        add(type, BorderLayout.CENTER);

        add(Box.createVerticalGlue());

        JLabel availability = new JLabel("Availability: " + this.component.getAvailability() + "%");
        availability.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        add(availability, BorderLayout.CENTER);

        JLabel cost = new JLabel("Cost: €" + this.component.getCost());
        cost.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        add(cost, BorderLayout.CENTER);

        add(Box.createVerticalStrut(10));
    }
}
