package com.nerdygadgets.monitoring.app.menus;

import com.nerdygadgets.monitoring.app.listeners.*;
import javax.swing.*;

public class MenuBar extends JMenuBar {

    private DesignerMenu designerMenu;

    public MenuBar(DesignerMenu designerMenu) {
        this.designerMenu = designerMenu;

        add(initializeAccountMenu());
        add(initializeDesignMenu());
        add(initializeMonitoringMenu());
    }

    public JMenu initializeAccountMenu() {
        JMenu menu = new JMenu("Account");

        JMenuItem menuItemMonitoringLoginText = new JMenuItem("Login");
        menuItemMonitoringLoginText.addActionListener(new LoginOpenActionListener());
        menu.add(menuItemMonitoringLoginText);

        JMenuItem menuItemMonitoringLogoutText = new JMenuItem("Logout");
        menuItemMonitoringLogoutText.addActionListener(new LogoutActionListener());
        menu.add(menuItemMonitoringLogoutText);

        return menu;
    }

    public JMenu initializeDesignMenu() {
        JMenu menu = new JMenu("Design");

        JMenuItem showWindowItem = new JMenuItem("Show window");
        showWindowItem.addActionListener(new DesignWindowOpenActionListener());
        menu.add(showWindowItem);

        JMenuItem menuItemOpenDesign = new JMenuItem("Open");
        menuItemOpenDesign.addActionListener(new DesignOpenActionListener());
        menu.add(menuItemOpenDesign);

        JMenuItem menuItemSaveDesign = new JMenuItem("Save");
        menuItemSaveDesign.addActionListener(new DesignSaveActionListener());
        menu.add(menuItemSaveDesign);

        JMenu subMenu = new JMenu("Components");

        JMenuItem componentsWebserversItem = new JMenuItem("Webservers");
        componentsWebserversItem.addActionListener(new WebserversActionListener(this.designerMenu));
        subMenu.add(componentsWebserversItem);

        JMenuItem componentsDatabasesItem = new JMenuItem("Databases");
        componentsDatabasesItem.addActionListener(new DatabasesActionListener(this.designerMenu));
        subMenu.add(componentsDatabasesItem);

        JMenuItem componentsRoutersItem = new JMenuItem("Routers");
        componentsRoutersItem.addActionListener(new RoutersActionListener(this.designerMenu));
        subMenu.add(componentsRoutersItem);

        menu.add(subMenu);

        return menu;
    }

    public JMenu initializeMonitoringMenu() {
        JMenu menu = new JMenu("Monitoring");

        JMenuItem menuItemMonitoringSwitchText = new JMenuItem("Show window");
        menuItemMonitoringSwitchText.addActionListener(new MonitoringWindowOpenActionListener());

        menu.add(menuItemMonitoringSwitchText);

        return menu;
    }
}
