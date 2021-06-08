package com.swingapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminGUI extends JPanel implements ActionListener {

    private JLabel usernameLabel, passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, logoutButton;


    public AdminGUI() {

        usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Login");
        logoutButton = new JButton("Logout");

        this.add(usernameLabel);
        this.add(usernameField);
        this.add(passwordLabel);
        this.add(passwordField);
        this.add(loginButton);
        this.add(logoutButton);

        loginButton.addActionListener(this);
        logoutButton.addActionListener(this);

//        logoutButton.setEnabled(false);

    }


    public static void createAndShowGUI() {

        // Create and set up the window
        JFrame window = new JFrame("Admin App");
        window.setVisible(true);
        window.setLocation(500, 200);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane
        Container container = window.getContentPane();
        AdminGUI contentPane = new AdminGUI();
        container.add(contentPane);

        // Display the window
        contentPane.setPreferredSize(new Dimension(300, 300));
        window.pack();
        contentPane.requestFocusInWindow();
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
