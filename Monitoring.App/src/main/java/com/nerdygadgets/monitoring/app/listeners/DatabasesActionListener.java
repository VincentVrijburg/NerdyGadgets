package com.nerdygadgets.monitoring.app.listeners;

import com.nerdygadgets.monitoring.app.menus.DesignerMenu;
import com.nerdygadgets.monitoring.app.frames.MainFrame;
import com.nerdygadgets.monitoring.data.entities.Component;
import com.nerdygadgets.monitoring.data.enums.ComponentType;
import com.nerdygadgets.monitoring.data.repository.entity.ComponentRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DatabasesActionListener implements ActionListener {

    private DesignerMenu designerMenu;
    protected JFrame dialogComponentFrameDatabases;

    public DatabasesActionListener(DesignerMenu designerMenu) {
        this.designerMenu = designerMenu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dialogComponentFrameDatabases = new JFrame();
        dialogComponentFrameDatabases.setSize(350, 175);
        dialogComponentFrameDatabases.setLocationRelativeTo(MainFrame.mainContainer);
        dialogComponentFrameDatabases.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ComponentRepository componentRepository = new ComponentRepository();
        List<Component> componentList = componentRepository.getByType(ComponentType.database);

        DefaultListModel<String> databaseList = new DefaultListModel<>();
        for (Component component: componentList) {
            databaseList.addElement(component.getName().toString());
        }

        this.designerMenu.listDatabases = new JList<>(databaseList);
        this.designerMenu.listDatabases.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.designerMenu.listDatabases.setLayoutOrientation(JList.VERTICAL);
        this.designerMenu.listDatabases.addMouseListener(this.designerMenu);

        JScrollPane listScroller = new JScrollPane(this.designerMenu.listDatabases);
        listScroller.setPreferredSize(new Dimension(250, 80));

        dialogComponentFrameDatabases.add(this.designerMenu.listDatabases);
        dialogComponentFrameDatabases.setVisible(true);
    }
}
