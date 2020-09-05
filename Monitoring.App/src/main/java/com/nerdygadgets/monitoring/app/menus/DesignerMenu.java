package com.nerdygadgets.monitoring.app.menus;

import com.nerdygadgets.monitoring.app.panels.DesignerPanel;
import com.nerdygadgets.monitoring.data.entities.Component;
import com.nerdygadgets.monitoring.data.enums.ComponentName;
import com.nerdygadgets.monitoring.data.repository.entity.ComponentRepository;
import com.nerdygadgets.monitoring.app.panels.ComponentPanel;
import com.nerdygadgets.monitoring.app.persistence.State;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DesignerMenu extends JMenuBar implements MouseListener {

    private ComponentRepository componentRepository = new ComponentRepository();

    protected JMenuBar menuBar;
    public JList<String> listWebservers = new JList<>();
    public JList<String> listDatabases = new JList<>();
    public JList<String> listRouters = new JList<>();

    public DesignerMenu()
    {
        this.menuBar = new MenuBar(this);
        setVisible(true);
    }

    public JMenuBar getMenuBar() {
        return this.menuBar;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        ComponentPanel componentPanel;
        Component component;
        String serverName = "";

        // Adding the component which has been clicked on
        if (e.getSource() == listWebservers) {
            // Getting the name value.
            serverName = listWebservers.getSelectedValue();
        } else if (e.getSource() == listDatabases) {
            // Getting the name value.
            serverName = listDatabases.getSelectedValue();
        } else if (e.getSource() == listRouters) {
            // Getting the name value.
            serverName = listRouters.getSelectedValue();
        }

        // Adding the name value to the list of added components.
        DesignerPanel.sidePanelBottom.model.addElement(serverName);

        // Getting the component based on its name, from the database.
        component = componentRepository.getByName(ComponentName.valueOf(serverName));

        // Creating a new instance of ComponentPanel to populate a panel with component data.
        componentPanel = new ComponentPanel(component);

        // Adding the new instance of ComponentPanel to the in memory list.
        State.DesignState.componentPanelList.add(componentPanel);

        // Adding the new instance of ComponentPanel to the (visual) gui screen.
        DesignerPanel.designPanel.add(componentPanel, BorderLayout.PAGE_START);

        // Updating the data inside the side panel bottom to reflect the new design
        DesignerPanel.sidePanelBottom.updateData();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}