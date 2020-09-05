package com.nerdygadgets.monitoring.app.listeners;

import com.nerdygadgets.monitoring.app.panels.DesignerPanel;
import com.nerdygadgets.monitoring.data.entities.Design;
import com.nerdygadgets.monitoring.data.entities.DesignComponent;
import com.nerdygadgets.monitoring.data.repository.entity.DesignRepository;
import com.nerdygadgets.monitoring.app.panels.ComponentPanel;
import com.nerdygadgets.monitoring.app.persistence.State;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DesignActionListener implements ActionListener {

    private DesignRepository designRepository = new DesignRepository();

    public DesignActionListener() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String id = DesignerPanel.designDialogFrame.designText.getText();
        Design design = designRepository.findById(id);
        DesignerPanel.designDialogFrame.designText.setText(null);

        DesignerPanel.sidePanelBottom.removeAllItemsFromList();

        for (DesignComponent designComponent: design.getDesignComponents()) {
            ComponentPanel componentPanel = new ComponentPanel(designComponent.getComponent());
            State.DesignState.componentPanelList.add(componentPanel);
            DesignerPanel.sidePanelBottom.model.addElement(designComponent.getComponent().getName().toString());
            DesignerPanel.designPanel.add(componentPanel, BorderLayout.PAGE_START);
        }

        DesignerPanel.designDialogFrame.setVisible(false);
        DesignerPanel.sidePanelBottom.updateData();
        DesignerPanel.designPanel.updatePanel();
    }
}
