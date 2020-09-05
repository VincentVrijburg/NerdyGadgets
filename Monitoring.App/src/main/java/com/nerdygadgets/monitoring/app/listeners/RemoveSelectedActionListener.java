package com.nerdygadgets.monitoring.app.listeners;

import com.nerdygadgets.monitoring.app.panels.DesignerPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveSelectedActionListener implements ActionListener {

    public RemoveSelectedActionListener() {
    }

    public void actionPerformed(ActionEvent e) {
        DesignerPanel.sidePanelBottom.removeSelectedItemFromList();
    }
}
