package com.swingapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AdminApp extends JPanel implements ActionListener {

    private JLabel usernameLabel, passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, logoutButton, deleteUserButton;
    private JTable userTable;
    private JFrame window;

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
        deleteUserButton = new JButton("Delete user");

        // Add components to the content pane
        this.add(usernameLabel);
        this.add(usernameField);
        this.add(passwordLabel);
        this.add(passwordField);
        this.add(loginButton);
        this.add(logoutButton);
        this.add(deleteUserButton);

        // Add action listeners to respond to button clicks
        loginButton.addActionListener(this);
        logoutButton.addActionListener(this);

        // Disable the log out and delete user buttons initially
        logoutButton.setEnabled(false);
        deleteUserButton.setEnabled(false);
    }


    /**
     * Creates and displays the Admin application's GUI
     */
    public void createAndShowGUI() {

        // Create and set up the window
//        JFrame window = new JFrame("Admin App");
        window = new JFrame("Admin App");
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


    private class LoginWorker extends SwingWorker<Boolean, Void> {
        private String usernameInput;
        private String passwordInput;

        protected LoginWorker(String usernameInput, String passwordInput) {
            this.usernameInput = usernameInput;
            this.passwordInput = passwordInput;
        }

        @Override
        protected Boolean doInBackground() throws Exception {
            LoginQuery loginQuery = new LoginQuery(this.usernameInput, this.passwordInput);
            return API.getInstance().authenticateUser(loginQuery);
        }

        @Override
        protected void done() {

            try {
                Boolean result = get();
                System.out.println("Result: " + result);

                if (result) {
                    UsersWorker usersWorker = new UsersWorker();
                    usersWorker.execute();
                } else {
                    JOptionPane.showMessageDialog(window, "Incorrect username and/or password");
                    loginButton.setEnabled(true);
                }

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }


    private class UsersWorker extends SwingWorker<List<User>, Void> {
        @Override
        protected List<User> doInBackground() throws Exception {
            return API.getInstance().getUserList();
        }

        @Override
        protected void done() {
            try {
                List<User> result = get();
                for (int i = 0; i < result.size(); i++) {
                    System.out.println(result.get(i).getFname());
                }
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

            loginButton.setEnabled(false);

            LoginWorker loginWorker = new LoginWorker(usernameInput, passwordInput);
            loginWorker.execute();
        }
        else if (e.getSource() == logoutButton) {
            usernameField.setText("Logged out!");
//            logoutButton.setEnabled(false);
        }
    }


    /**
     * Starts the Admin application
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AdminApp adminApp = new AdminApp();
                adminApp.createAndShowGUI();
            }
        });
    }

}
