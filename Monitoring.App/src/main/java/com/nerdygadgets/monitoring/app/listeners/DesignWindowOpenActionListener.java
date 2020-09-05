package com.nerdygadgets.monitoring.app.listeners;

import com.nerdygadgets.monitoring.app.frames.MainFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DesignWindowOpenActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.setDesignVisible();
    }
}
