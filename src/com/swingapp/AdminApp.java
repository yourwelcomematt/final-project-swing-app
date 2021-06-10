package com.swingapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

        // Create table
        String[] columnNames = {"ID", "fName", "lName", "Username", "DOB", "Password", "Description", "Profile Image", "AuthToken", "Admin", "NumArticles"};

        DefaultTableModel model = new DefaultTableModel(0, columnNames.length);
        model.setColumnIdentifiers(columnNames);
        userTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(userTable);

        // Set layout of the content pane
        this.setLayout(new BorderLayout());

        JPanel usernamePane = new JPanel();
        usernamePane.setLayout(new BoxLayout(usernamePane, BoxLayout.LINE_AXIS));
        usernamePane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        usernamePane.add(usernameLabel);
        usernamePane.add(usernameField);

        JPanel passwordPane = new JPanel();
        passwordPane.setLayout(new BoxLayout(passwordPane, BoxLayout.LINE_AXIS));
        passwordPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        passwordPane.add(passwordLabel);
        passwordPane.add(passwordField);

        JPanel inputsPane = new JPanel();
        inputsPane.setLayout(new BoxLayout(inputsPane, BoxLayout.LINE_AXIS));
        inputsPane.add(usernamePane);
        inputsPane.add(passwordPane);

        JPanel buttonsPane = new JPanel();
        buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.LINE_AXIS));
        buttonsPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        buttonsPane.add(loginButton);
        buttonsPane.add(logoutButton);

        JPanel loginPane = new JPanel();
        loginPane.setLayout(new BoxLayout(loginPane, BoxLayout.PAGE_AXIS));
        loginPane.add(inputsPane);
        loginPane.add(buttonsPane);

        // Add components to the content pane
        this.add(loginPane, BorderLayout.PAGE_START);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(deleteUserButton, BorderLayout.PAGE_END);

        // Add action listeners to respond to button clicks
        loginButton.addActionListener(this);
        logoutButton.addActionListener(this);
        deleteUserButton.addActionListener(this);

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
        contentPane.setPreferredSize(new Dimension(700, 500));
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
                DefaultTableModel model = (DefaultTableModel) userTable.getModel();

                for (int i = 0; i < result.size(); i++) {
                    User user = result.get(i);
//                    System.out.println(user.toString());
                    Object[] row = {user.getId(), user.getFname(), user.getLname(), user.getUsername(), user.getDob(), user.getPassword(), user.getDescription(), user.getImageSource(), user.getAuthToken(), user.getAdmin(), user.getNumArticles()};
                    model.addRow(row);
                }

                logoutButton.setEnabled(true);
                deleteUserButton.setEnabled(true);
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
        else if (e.getSource() == deleteUserButton) {
            JOptionPane.showMessageDialog(window, "User deleted!");
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
