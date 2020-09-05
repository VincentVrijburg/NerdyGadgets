package com.nerdygadgets.monitoring.app.GUI;

import com.nerdygadgets.monitoring.app.frames.MainFrame;
import com.nerdygadgets.monitoring.app.menus.DesignerMenu;

public class Application {
    public static void main(String[] args)
    {
        MainFrame mainFrame = new MainFrame("NerdyGadgets - Infrastructure design and monitoring");
        DesignerMenu designerMenu = new DesignerMenu();
        mainFrame.setJMenuBar(designerMenu.getMenuBar());

        MainFrame.setDesignVisible();
    }
}
