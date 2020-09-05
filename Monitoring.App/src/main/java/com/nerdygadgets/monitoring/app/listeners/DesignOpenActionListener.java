package com.nerdygadgets.monitoring.app.listeners;

import com.nerdygadgets.monitoring.app.panels.DesignerPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DesignOpenActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        DesignerPanel.designDialogFrame.setVisible(true);
    }
}
