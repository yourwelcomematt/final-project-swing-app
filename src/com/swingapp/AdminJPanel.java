package com.swingapp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminJPanel extends JPanel implements ActionListener {

    private JLabel usernameLabel, passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, logoutButton;

    public AdminJPanel() {

        usernameLabel = new JLabel("Username:");
        this.add(usernameLabel);

        usernameField = new JTextField(15);
        this.add(usernameField);

        passwordLabel = new JLabel("Password:");
        this.add(passwordLabel);

        passwordField = new JPasswordField(15);
        this.add(passwordField);

        loginButton = new JButton("Login");
        this.add(loginButton);
        loginButton.addActionListener(this);

        logoutButton = new JButton("Logout");
        this.add(logoutButton);
        logoutButton.addActionListener(this);

//        logoutButton.setEnabled(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            usernameField.setText("Log in!");
        }
        else if (e.getSource() == logoutButton) {
            usernameField.setText("Log out!");
        }
    }
}
