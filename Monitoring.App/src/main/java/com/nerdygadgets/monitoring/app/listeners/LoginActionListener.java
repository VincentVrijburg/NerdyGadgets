package com.nerdygadgets.monitoring.app.listeners;

import com.nerdygadgets.monitoring.app.panels.DesignerPanel;
import com.nerdygadgets.monitoring.app.frames.MainFrame;
import com.nerdygadgets.monitoring.app.persistence.State;
import com.nerdygadgets.monitoring.data.entities.User;
import com.nerdygadgets.monitoring.data.repository.entity.UserRepository;
import com.nerdygadgets.monitoring.data.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginActionListener implements ActionListener {

    private UserRepository userRepository = new UserRepository();

    public LoginActionListener() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = DesignerPanel.loginDialogFrame.userText.getText();
        String plainPassword = DesignerPanel.loginDialogFrame.passwordText.getText();

        DesignerPanel.loginDialogFrame.dialogErrorMessage.setForeground(Color.black);
        DesignerPanel.loginDialogFrame.dialogErrorMessage.setText(null);

        // Check if the fields have values
        if (username.isEmpty() || plainPassword.isEmpty()) {
            DesignerPanel.loginDialogFrame.dialogErrorMessage.setText("Don't leave empty fields!");
            return;
        }

        // Retrieve the user by the given username
        User user = userRepository.findByUsername(DesignerPanel.loginDialogFrame.userText.getText());

        // Validate the password
        if (user != null && Utils.PasswordUtils.verifyPassword(plainPassword, user.getPassword())) {
            // Valid password; proceed
            State.LoginState.isLoggedIn = true;
            State.LoginState.user = user;

            DesignerPanel.loginDialogFrame.setVisible(false);
            JOptionPane.showMessageDialog(MainFrame.mainContainer, "You have successfully logged in");

            DesignerPanel.loginDialogFrame.userText.setText(null);
            DesignerPanel.loginDialogFrame.passwordText.setText(null);
        } else {
            // Invalid password in this situation
            State.LoginState.isLoggedIn = false;

            DesignerPanel.loginDialogFrame.dialogErrorMessage.setForeground(Color.red);
            DesignerPanel.loginDialogFrame.dialogErrorMessage.setText("Invalid username and password combination.");
        }
    }
}
