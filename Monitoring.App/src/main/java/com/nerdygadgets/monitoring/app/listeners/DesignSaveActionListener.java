package com.nerdygadgets.monitoring.app.listeners;

import com.nerdygadgets.monitoring.app.frames.MainFrame;
import com.nerdygadgets.monitoring.app.persistence.State;
import com.nerdygadgets.monitoring.data.entities.Component;
import com.nerdygadgets.monitoring.data.entities.Design;
import com.nerdygadgets.monitoring.data.entities.DesignComponent;
import com.nerdygadgets.monitoring.data.repository.entity.DesignComponentRepository;
import com.nerdygadgets.monitoring.data.repository.entity.DesignRepository;
import com.nerdygadgets.monitoring.app.panels.ComponentPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DesignSaveActionListener implements ActionListener {

    private DesignRepository designRepository = new DesignRepository();
    private DesignComponentRepository designComponentRepository = new DesignComponentRepository();

    public DesignSaveActionListener() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Saving the file to the database...");

        Date dateNow = new Date();

        Design design = new Design();
        design.setUpdated(dateNow);
        design.setCreated(dateNow);

        if (State.LoginState.isLoggedIn && State.LoginState.user != null) {
            design.setUser(State.LoginState.user);
        }

        designRepository.persist(design);

        // Create and save the design components and attach it to the design.
        List<Component> componentList = State.DesignState.componentPanelList
                .stream()
                .map(ComponentPanel::getComponent)
                .collect(Collectors.toList());

        for (Component component: componentList) {
            DesignComponent designComponent = new DesignComponent();
            designComponent.setComponent(component);
            designComponent.setDesign(design);
            designComponent.setUpdated(dateNow);
            designComponent.setCreated(dateNow);

            designComponentRepository.persist(designComponent);
        }

        JOptionPane.showInputDialog(
                MainFrame.mainContainer,
                "Copy the design identifier for future use",
                "Design has been saved",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                design.getId());
    }
}
