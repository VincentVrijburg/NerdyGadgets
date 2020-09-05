package com.nerdygadgets.monitoring.app.panels;

import com.nerdygadgets.monitoring.app.listeners.RemoveAllActionListener;
import com.nerdygadgets.monitoring.app.listeners.RemoveSelectedActionListener;
import com.nerdygadgets.monitoring.app.persistence.State;
import com.nerdygadgets.monitoring.data.entities.Component;
import com.nerdygadgets.monitoring.data.enums.ComponentType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.DecimalFormat;

public class SidePanelBottom extends JPanel {
    private static final long serialVersionUID = 1L;
    private static DecimalFormat percentageFormat = new DecimalFormat("#.###'%'");
    private static DecimalFormat currencyFormat = new DecimalFormat("'€'0.00");

    private JLabel availability, costs;

    public final DefaultListModel<String> model = new DefaultListModel<>();
    public final JList<String> listSidePanel = new JList<>(model);

    public SidePanelBottom() {
        initializePanel();
    }

    public void initializePanel() {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new GridLayout(15, 1));
        setBackground(Color.LIGHT_GRAY);
        setMaximumSize(new Dimension(DesignerPanel.rightPanel.getPreferredSize().width, 10000));

        add(new JLabel("Design details", SwingConstants.CENTER));

        add(new JLabel("Components list:"));

        listSidePanel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listSidePanel.setLayoutOrientation(JList.VERTICAL);

        //Scrollbar
        JScrollPane listScroller = new JScrollPane(listSidePanel);
        listScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(listScroller);

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new RemoveSelectedActionListener());
        add(removeButton);

        JButton removeAllButton = new JButton("Remove all");
        removeAllButton.addActionListener(new RemoveAllActionListener());
        add(removeAllButton);

        add(new JLabel("Total costs"));
        this.costs = new JLabel(currencyFormat.format(getTotalCost()));
        add(costs);

        add(new JLabel("Availability"));
        this.availability = new JLabel(percentageFormat.format(getCalculatedAvailability()));
        add(availability);
    }

    public void removeSelectedItemFromList() {
        int index = listSidePanel.getSelectedIndex();
        if (index < 0)
            return;

        String name = this.model.getElementAt(index);
        ComponentPanel componentPanel = State.DesignState.componentPanelList
                .stream()
                .filter(p -> p.getComponent().getName().toString() == name)
                .findFirst()
                .get();

        State.DesignState.componentPanelList.remove(componentPanel);
        this.model.remove(index);
        DesignerPanel.designPanel.remove(componentPanel);

        updateData();
    }

    public void removeAllItemsFromList() {
        State.DesignState.componentPanelList.clear();
        this.model.clear();
        DesignerPanel.designPanel.removeAll();

        updateData();
    }

    public Double getCalculatedAvailability() {
        if (State.DesignState.componentPanelList.isEmpty()) {
            return 0.0;
        }

        if (!State.DesignState.componentPanelList.stream().anyMatch(o -> o.getComponent().getType() == ComponentType.firewall)) {
            return 0.0;
        }

        if (!State.DesignState.componentPanelList.stream().anyMatch(o -> o.getComponent().getType() == ComponentType.database)) {
            return 0.0;
        }

        if (!State.DesignState.componentPanelList.stream().anyMatch(o -> o.getComponent().getType() == ComponentType.webserver)) {
            return 0.0;
        }

        // Calculate the webservers
        double[] availabilitiesWeb = State.DesignState.componentPanelList
                .stream()
                .filter(serverPanel -> serverPanel.getComponent().getType() == ComponentType.webserver)
                .map(ComponentPanel::getComponent)
                .mapToDouble(Component::getAvailability)
                .toArray();

        double availabilityWebservers = 0;
        for (double availability: availabilitiesWeb) {
            double value = availability / 100;
            if (availabilityWebservers == 0) {
                availabilityWebservers = (1 - value);
            } else {
                availabilityWebservers *= (1 - value);
            }
        }

        double calculatedAvailabilityWeb = 1 - (availabilityWebservers);

        double[] availabilitiesDatabase = State.DesignState.componentPanelList
                .stream()
                .filter(serverPanel -> serverPanel.getComponent().getType() == ComponentType.database)
                .map(ComponentPanel::getComponent)
                .mapToDouble(Component::getAvailability)
                .toArray();

        double availabilityDatabases = 0;
        for (double availability: availabilitiesDatabase) {
            double value = availability / 100;
            if (availabilityDatabases == 0) {
                availabilityDatabases = (1 - value);
            } else {
                availabilityDatabases *= (1 - value);
            }
        }

        double calculatedAvailabilityDatabase = 1 - (availabilityDatabases);

        double[] availabilitiesFirewall = State.DesignState.componentPanelList
                .stream()
                .filter(serverPanel -> serverPanel.getComponent().getType() == ComponentType.firewall)
                .map(ComponentPanel::getComponent)
                .mapToDouble(Component::getAvailability)
                .toArray();

        double availabilityFirewalls = 0;
        for (double availability: availabilitiesFirewall) {
            double value = availability / 100;
            if (availabilityFirewalls == 0) {
                availabilityFirewalls = (1 - value);
            } else {
                availabilityFirewalls *= (1 - value);
            }
        }

        double calculatedAvailabilityFirewall = 1 - (availabilityFirewalls);

        return (calculatedAvailabilityWeb * calculatedAvailabilityDatabase * calculatedAvailabilityFirewall) * 100;
    }

    public double getTotalCost() {
        return State.DesignState.componentPanelList
                .stream()
                .map(ComponentPanel::getComponent)
                .mapToDouble(Component::getCost)
                .sum();
    }

    public void updateData() {
        this.availability.setText(percentageFormat.format(getCalculatedAvailability()));
        this.costs.setText(currencyFormat.format(getTotalCost()));

        revalidate();
        DesignerPanel.designPanel.updatePanel();
    }
}
