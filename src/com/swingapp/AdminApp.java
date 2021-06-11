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


    /**
     * SwingWorker which handles log ins
     */
    private class LoginWorker extends SwingWorker<Boolean, Void> {
        private String usernameInput;
        private String passwordInput;

        // LoginWorker constructor
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

                // If the user is an admin, call the UsersWorker
                if (result) {
                    UsersWorker usersWorker = new UsersWorker();
                    usersWorker.execute();
                // If the user not an admin, display an error message
                } else {
                    JOptionPane.showMessageDialog(window, "Incorrect username and/or password and/or not an admin");
                    loginButton.setEnabled(true);
                }

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * SwingWorker which retrieves all users and displays them in the JTable
     */
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

                // Iterates through the list of users and adds them to the table
                for (int i = 0; i < result.size(); i++) {
                    User user = result.get(i);

                    Object[] row = {user.getId(), user.getFname(), user.getLname(), user.getUsername(), user.getDob(), user.getPassword(), user.getDescription(), user.getImageSource(), user.getAuthToken(), user.getAdmin(), user.getNumArticles()};
                    model.addRow(row);
                }

                JOptionPane.showMessageDialog(window, "Log in successful!");
                logoutButton.setEnabled(true);
                deleteUserButton.setEnabled(true);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * SwingWorker which handles log outs
     */
    private class LogoutWorker extends SwingWorker<Void, Void> {
        @Override
        protected Void doInBackground() throws Exception {
            API.getInstance().logoutUser();
            return null;
        }

        // Clears the username field, password field, and table
        @Override
        protected void done() {
            usernameField.setText("");
            passwordField.setText("");
            DefaultTableModel model = (DefaultTableModel) userTable.getModel();
            model.setRowCount(0);
            JOptionPane.showMessageDialog(window, "Log out successful!");
            loginButton.setEnabled(true);
        }
    }


    /**
     * SwingWorker which handles deleting of users
     */
    private class DeleteUserWorker extends SwingWorker<Void, Void> {

        private Object id;

        protected DeleteUserWorker(Object id) {
            this.id = id;
        }

        @Override
        protected Void doInBackground() throws Exception {
            API.getInstance().deleteUser(id);
            return null;
        }

        @Override
        protected void done() {
            JOptionPane.showMessageDialog(window, "User deleted!");
            deleteUserButton.setEnabled(true);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // When the login button is clicked, get the username and password inputs,
        // disable the login button, create a new LoginWorker and start it
        if (e.getSource() == loginButton) {
            String usernameInput = usernameField.getText();
            String passwordInput = String.valueOf(passwordField.getPassword());

            loginButton.setEnabled(false);

            LoginWorker loginWorker = new LoginWorker(usernameInput, passwordInput);
            loginWorker.execute();
        }
        // When the logout button is clicked, disable the logout and delete user buttons,
        // create a new LogoutWorker, and start it
        else if (e.getSource() == logoutButton) {
            logoutButton.setEnabled(false);
            deleteUserButton.setEnabled(false);
            LogoutWorker logoutWorker = new LogoutWorker();
            logoutWorker.execute();
        }
        // When the delete user button is clicked, disable the delete user buttons,
        // retrieve the table model, get the selected row, get the id of the user at the
        // selected row, remove the selected row, create a new DeleteUserWorker, and start it
        else if (e.getSource() == deleteUserButton) {
            deleteUserButton.setEnabled(false);

            DefaultTableModel model = (DefaultTableModel) userTable.getModel();
            int selectedRow = userTable.getSelectedRow();
            Object selectedId = userTable.getValueAt(selectedRow, 0);

            model.removeRow(selectedRow);

            DeleteUserWorker deleteUserWorker = new DeleteUserWorker(selectedId);
            deleteUserWorker.execute();
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
