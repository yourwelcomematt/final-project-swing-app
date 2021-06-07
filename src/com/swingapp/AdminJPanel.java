package com.swingapp;

import javax.swing.*;

public class AdminJPanel extends JPanel {

    public AdminJPanel() {

        JLabel usernameLabel = new JLabel("Username:");
        this.add(usernameLabel);

        JTextField usernameField = new JTextField(15);
        this.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        this.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField(15);
        this.add(passwordField);

        JButton loginButton = new JButton("Login");
        this.add(loginButton);

        JButton logoutButton = new JButton("Logout");
        this.add(logoutButton);

        logoutButton.setEnabled(false);

    }

}
