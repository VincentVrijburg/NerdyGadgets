package com.nerdygadgets.monitoring.app.listeners;

import com.nerdygadgets.monitoring.app.panels.DesignerPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveAllActionListener implements ActionListener {

    public RemoveAllActionListener() {
    }

    public void actionPerformed(ActionEvent e) {
        DesignerPanel.sidePanelBottom.removeAllItemsFromList();
    }
}
