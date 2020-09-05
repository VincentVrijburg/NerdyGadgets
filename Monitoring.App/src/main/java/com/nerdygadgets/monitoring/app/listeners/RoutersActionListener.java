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

public class RoutersActionListener implements ActionListener {

    private DesignerMenu designerMenu;
    protected JFrame dialogComponentFrameRouters;

    public RoutersActionListener(DesignerMenu designerMenu) {
        this.designerMenu = designerMenu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dialogComponentFrameRouters = new JFrame();
        dialogComponentFrameRouters.setSize(350, 175);
        dialogComponentFrameRouters.setLocationRelativeTo(MainFrame.mainContainer);
        dialogComponentFrameRouters.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ComponentRepository componentRepository = new ComponentRepository();
        List<Component> componentList = componentRepository.getByType(ComponentType.firewall);

        DefaultListModel<String> routerList = new DefaultListModel<>();
        for (Component component: componentList) {
            routerList.addElement(component.getName().toString());
        }

        this.designerMenu.listRouters = new JList<>(routerList);
        this.designerMenu.listRouters.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.designerMenu.listRouters.setLayoutOrientation(JList.VERTICAL);
        this.designerMenu.listRouters.addMouseListener(this.designerMenu);

        JScrollPane listScroller = new JScrollPane(this.designerMenu.listRouters);
        listScroller.setPreferredSize(new Dimension(250, 80));

        dialogComponentFrameRouters.add(this.designerMenu.listRouters);
        dialogComponentFrameRouters.setVisible(true);
    }
}