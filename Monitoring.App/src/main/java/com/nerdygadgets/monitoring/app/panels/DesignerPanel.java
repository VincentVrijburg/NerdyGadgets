package com.nerdygadgets.monitoring.app.panels;

import com.nerdygadgets.monitoring.app.frames.DesignDialogFrame;
import com.nerdygadgets.monitoring.app.frames.LoginDialogFrame;
import com.nerdygadgets.monitoring.app.frames.MainFrame;
import com.nerdygadgets.monitoring.app.panels.DesignPanel;
import com.nerdygadgets.monitoring.app.panels.RightPanel;
import com.nerdygadgets.monitoring.app.panels.SidePanelBottom;
import com.nerdygadgets.monitoring.app.panels.SidePanelTop;

import javax.swing.*;
import java.awt.*;

public class DesignerPanel extends JPanel {

    public static DesignPanel designPanel;
    public static RightPanel rightPanel;
    public static SidePanelTop sidePanelTop;
    public static SidePanelBottom sidePanelBottom;
    public static LoginDialogFrame loginDialogFrame;
    public static DesignDialogFrame designDialogFrame;

    public DesignerPanel() {
        // Initializing the main panel and adding it to the mainframe container.
        designPanel = new DesignPanel();
        MainFrame.mainContainer.add(designPanel);

        // Initializing the right panel and adding it to the mainframe container.
        rightPanel = new RightPanel();
        MainFrame.mainContainer.add(rightPanel, BorderLayout.EAST);

        // Initializing the side panel top and adding it to the right panel.
        sidePanelTop = new SidePanelTop();
        rightPanel.add(sidePanelTop, BorderLayout.NORTH);

        // Initializing the side panel bottom and adding it to the right panel.
        sidePanelBottom = new SidePanelBottom();
        rightPanel.add(sidePanelBottom, BorderLayout.SOUTH);

        // Initializing the dialog frames.
        loginDialogFrame = new LoginDialogFrame();
        designDialogFrame = new DesignDialogFrame();

        // Set the designer frame visible.
        setVisible(true);
    }

    // Method to add the current frame to the main container of the application.
    public void addToMainContainer() {
        MainFrame.mainContainer.add(designPanel);
        MainFrame.mainContainer.add(rightPanel, BorderLayout.EAST);
    }
}