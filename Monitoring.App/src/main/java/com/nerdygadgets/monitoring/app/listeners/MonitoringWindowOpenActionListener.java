package com.nerdygadgets.monitoring.app.listeners;

import com.nerdygadgets.monitoring.app.frames.MainFrame;
import com.nerdygadgets.monitoring.app.persistence.State;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MonitoringWindowOpenActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!State.LoginState.isLoggedIn) {
            JOptionPane.showMessageDialog(MainFrame.mainContainer,"You have to login first to access the monitoring window");
            return;
        }

        MainFrame.setMonitoringVisible();
    }
}
