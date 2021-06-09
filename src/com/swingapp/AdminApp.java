package com.swingapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

public class AdminApp extends JPanel implements ActionListener {

    private JLabel usernameLabel, passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, logoutButton, deleteUserButton;
    private JTable userTable;

    /**
     * Constructor method to create the Admin application's content pane
     */
    public AdminApp() {

        // Create basic GUI components
        usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Login");
        logoutButton = new JButton("Logout");

        // Add components to the content pane
        this.add(usernameLabel);
        this.add(usernameField);
        this.add(passwordLabel);
        this.add(passwordField);
        this.add(loginButton);
        this.add(logoutButton);

        // Add action listeners to respond to button clicks
        loginButton.addActionListener(this);
        logoutButton.addActionListener(this);

        // Disable the log out button initially
        logoutButton.setEnabled(false);

    }


    /**
     * Creates and displays the Admin application's GUI
     */
    public static void createAndShowGUI() {

        // Create and set up the window
        JFrame window = new JFrame("Admin App");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane
        AdminApp contentPane = new AdminApp();
        window.add(contentPane);

        // Display the window
        contentPane.setPreferredSize(new Dimension(300, 300));
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        contentPane.requestFocusInWindow();

    }


    private class LoginWorker extends SwingWorker<String, Void> {
        private String usernameInput;
        private String passwordInput;

        protected LoginWorker(String usernameInput, String passwordInput) {
            this.usernameInput = usernameInput;
            this.passwordInput = passwordInput;
        }

        @Override
        protected String doInBackground() throws Exception {
            LoginQuery loginQuery = new LoginQuery(this.usernameInput, this.passwordInput);
            return API.getInstance().authenticateUser(loginQuery);
        }

        @Override
        protected void done() {
            logoutButton.setEnabled(true);

            try {
                String result = get();
//                System.out.println(result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String usernameInput = usernameField.getText();
            String passwordInput = String.valueOf(passwordField.getPassword());

            System.out.println(usernameInput);
            System.out.println(passwordInput);

            loginButton.setEnabled(false);

            LoginWorker loginWorker = new LoginWorker(usernameInput, passwordInput);
            loginWorker.execute();
        }
        else if (e.getSource() == logoutButton) {
            usernameField.setText("Logged out!");
//            logoutButton.setEnabled(false);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
