package com.nerdygadgets.monitoring.app.frames;

import com.nerdygadgets.monitoring.app.listeners.DesignActionListener;

import javax.swing.*;

public class DesignDialogFrame extends JFrame {

    public JPanel dialogDesignPanel;
    public JLabel dialogErrorMessage, designLabel;
    public JTextField designText;
    public JButton dialogOpenButton;

    public DesignDialogFrame() {
        initializeFrame();
    }

    private void initializeFrame() {
        dialogDesignPanel = new JPanel();

        // Setting numerous properties for the current design dialog frame.
        setSize(350, 175);
        setLocationRelativeTo(MainFrame.mainContainer);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        add(dialogDesignPanel);

        dialogDesignPanel.setLayout(null);

        // Initializing the identifier label and setting its properties.
        designLabel = new JLabel("Identifier");
        designLabel.setBounds(10, 20, 80, 25);
        dialogDesignPanel.add(designLabel);

        // Initializing the identifier textfield and setting its properties.
        designText = new JTextField(20);
        designText.setBounds(100, 20, 165, 25);
        dialogDesignPanel.add(designText);

        // Initializing the open button and setting its properties.
        dialogOpenButton = new JButton("Open");
        dialogOpenButton.setBounds(10,80,80,25);
        dialogOpenButton.addActionListener(new DesignActionListener());
        dialogDesignPanel.add(dialogOpenButton);

        // Initializing the error message label and setting its properties.
        dialogErrorMessage = new JLabel();
        dialogErrorMessage.setBounds(10,110,300,25);
        dialogErrorMessage.setText("");
        dialogDesignPanel.add(dialogErrorMessage);
    }
}
