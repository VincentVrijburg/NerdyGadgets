package com.nerdygadgets.monitoring.app.listeners;

import com.nerdygadgets.monitoring.app.panels.DesignerPanel;
import com.nerdygadgets.monitoring.app.backtracking.AvailabilityCalculator;
import com.nerdygadgets.monitoring.app.backtracking.models.CalculatedComponent;
import com.nerdygadgets.monitoring.app.backtracking.models.CalculatedDesign;
import com.nerdygadgets.monitoring.data.entities.Component;
import com.nerdygadgets.monitoring.data.repository.entity.ComponentRepository;
import org.json.simple.parser.ParseException;
import com.nerdygadgets.monitoring.app.panels.ComponentPanel;
import com.nerdygadgets.monitoring.app.panels.SidePanelTop;
import com.nerdygadgets.monitoring.app.persistence.State;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DesignCreateActionListener implements ActionListener {

    private AvailabilityCalculator availabilityCalculator = new AvailabilityCalculator();
    private ComponentRepository componentRepository = new ComponentRepository();

    private SidePanelTop sidePanelTop;

    public DesignCreateActionListener(SidePanelTop sidePanelTop) {
        this.sidePanelTop = sidePanelTop;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            double userPercentage = Double.parseDouble(this.sidePanelTop.percentageField.getText());
            CalculatedDesign suggestedDesign = availabilityCalculator.getCalcualtedDesign(userPercentage);

            DesignerPanel.sidePanelBottom.removeAllItemsFromList();

            for (CalculatedComponent calculatedComponent: suggestedDesign.components) {
                if (calculatedComponent.amount == 0)
                    continue;

                Component component = componentRepository.getByName(calculatedComponent.componentName);

                for (int i = 0; i < calculatedComponent.amount; i++) {
                    ComponentPanel componentPanel = new ComponentPanel(component);
                    State.DesignState.componentPanelList.add(componentPanel);
                    DesignerPanel.sidePanelBottom.model.addElement(component.getName().toString());
                    DesignerPanel.designPanel.add(componentPanel, BorderLayout.PAGE_START);
                }
            }

            DesignerPanel.sidePanelBottom.updateData();
            DesignerPanel.designPanel.updatePanel();

        } catch (ParseException parseException) {
            System.out.println(parseException.getMessage());
        }
    }
}
