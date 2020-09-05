package com.nerdygadgets.monitoring.app.frames;

import com.nerdygadgets.monitoring.app.listeners.LoginActionListener;

import javax.swing.*;

public class LoginDialogFrame extends JFrame {
    public JPanel dialogLoginPanel;
    public JLabel dialogErrorMessage, userLabel, passwordLabel;
    public JTextField userText;
    public JPasswordField passwordText;
    public JButton dialogLoginButton;

    public LoginDialogFrame() {
        initializeFrame();
    }

    private void initializeFrame() {
        dialogLoginPanel = new JPanel();

        // Setting numerous properties for the current login dialog frame.
        setSize(350, 175);
        setLocationRelativeTo(MainFrame.mainContainer);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        add(dialogLoginPanel);

        dialogLoginPanel.setLayout(null);

        // Initializing the username label and setting its properties.
        userLabel = new JLabel("Username");
        userLabel.setBounds(10, 20, 80, 25);
        dialogLoginPanel.add(userLabel);

        // Initializing the username textfield and setting its properties.
        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        dialogLoginPanel.add(userText);

        // Initializing the password label and setting its properties.
        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10,50,80, 25);
        dialogLoginPanel.add(passwordLabel);

        // Initializing the password passwordfield and setting its properties.
        passwordText = new JPasswordField();
        passwordText.setBounds(100, 50, 165, 25);
        dialogLoginPanel.add(passwordText);

        // Initializing the login button and setting its properties.
        dialogLoginButton = new JButton("Login");
        dialogLoginButton.setBounds(10,80,80,25);
        dialogLoginButton.addActionListener(new LoginActionListener());
        dialogLoginPanel.add(dialogLoginButton);

        // Initializing the error message label and setting its properties.
        dialogErrorMessage = new JLabel();
        dialogErrorMessage.setBounds(10,110,300,25);
        dialogErrorMessage.setText("");
        dialogLoginPanel.add(dialogErrorMessage);
    }
}
