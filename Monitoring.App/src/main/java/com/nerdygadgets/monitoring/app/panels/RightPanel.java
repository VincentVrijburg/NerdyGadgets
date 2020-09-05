package com.nerdygadgets.monitoring.app.panels;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class RightPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public RightPanel() {
        initializePanel();
    }

    public void initializePanel() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(300, 400));
    }



}
