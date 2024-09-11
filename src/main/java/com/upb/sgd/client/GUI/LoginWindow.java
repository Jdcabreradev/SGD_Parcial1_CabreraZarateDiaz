package com.upb.sgd.client.GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.upb.sgd.shared.domain.User;
import com.upb.sgd.shared.infrastructure.rmi.clientapp.ClientAppUsersRMI;

/**
 *
 * @author jj
 */
public class LoginWindow {
    private JFrame loginFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTabbedPane tabbedPane;
    private User user;

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            // Locating external registry
            Registry externalRegistry = LocateRegistry.getRegistry("localhost", 1802);
            ClientAppUsersRMI rmi = (ClientAppUsersRMI) externalRegistry.lookup("appserver/user");

            // Authenticating user
            user = rmi.Login(username, password);

            if (user != null) {
                showSuccessTab();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Login Failed", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(loginFrame, "Something went wrong", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showLogin() {
        // Main window
        loginFrame = new JFrame("User Login");
        loginFrame.setSize(400, 200);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLayout(new BorderLayout());

        // Login panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2));

        // User input
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);

        JButton loginButton = new JButton("Log in");
        loginPanel.add(new JLabel());
        loginPanel.add(loginButton);

        loginButton.addActionListener((ActionEvent e) -> {
            handleLogin();
        });

        tabbedPane = new JTabbedPane();

        // Show login screen
        loginFrame.setVisible(true);
        loginFrame.add(loginPanel, BorderLayout.CENTER);
        loginFrame.setVisible(true);
    }

    private void showSuccessTab() {
        JPanel successPanel = new JPanel();
        successPanel.add(new JLabel("User logged in successfully!"));

        // Adding successPanel to tabbedPane
        tabbedPane.addTab("Home", successPanel);

        loginFrame.getContentPane().removeAll();
        loginFrame.add(tabbedPane);
        loginFrame.revalidate();
        loginFrame.repaint();
    }
}
