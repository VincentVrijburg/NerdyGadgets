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

public class WebserversActionListener implements ActionListener {

    private DesignerMenu designerMenu;
    private ComponentRepository componentRepository = new ComponentRepository();

    protected JFrame dialogComponentFrameWebservers;

    public WebserversActionListener(DesignerMenu designerMenu) {
        this.designerMenu = designerMenu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dialogComponentFrameWebservers = new JFrame();
        dialogComponentFrameWebservers.setSize(350, 175);
        dialogComponentFrameWebservers.setLocationRelativeTo(MainFrame.mainContainer);
        dialogComponentFrameWebservers.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        List<Component> componentList = componentRepository.getByType(ComponentType.webserver);

        DefaultListModel<String> webserverList = new DefaultListModel<>();
        for (Component component: componentList) {
            webserverList.addElement(component.getName().toString());
        }

        this.designerMenu.listWebservers = new JList<>(webserverList);
        this.designerMenu.listWebservers.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.designerMenu.listWebservers.setLayoutOrientation(JList.VERTICAL);
        this.designerMenu.listWebservers.addMouseListener(this.designerMenu);

        JScrollPane listScroller = new JScrollPane(this.designerMenu.listWebservers);
        listScroller.setPreferredSize(new Dimension(250, 80));

        dialogComponentFrameWebservers.add(this.designerMenu.listWebservers);
        dialogComponentFrameWebservers.setVisible(true);
    }
}
