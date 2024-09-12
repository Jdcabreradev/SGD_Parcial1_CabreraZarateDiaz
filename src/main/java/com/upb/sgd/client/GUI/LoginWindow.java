package com.upb.sgd.client.GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.upb.sgd.client.GUI.Interface.AbstractGUIWindow;
import com.upb.sgd.client.Mediator.ClientMediator;
import com.upb.sgd.shared.domain.User;

/**
 *
 * @author jj
 */
public class LoginWindow extends AbstractGUIWindow {
    private JFrame loginFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTabbedPane tabbedPane;
    // private User user;

    public LoginWindow(ClientMediator mediator) {
        super(mediator);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        try {
            // Saving current user to the mediator
            this.mediator.loggedUser = this.mediator.userService.Login(username, password);
            if (this.mediator.loggedUser != null) {
                if (this.mediator.loggedUser.isAdmin) {
                    showAdminPanel();
                } else
                    showSuccessTab();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Login Failed", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (RemoteException | NullPointerException e) {
            System.err.println(e.getMessage());
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

    private void showAdminPanel() {  // NOT WORKING YET!!!
        JPanel adminPanel = new JPanel();
        adminPanel.add(new JLabel("Admin Panel"), BorderLayout.NORTH);

        // User list
        String[] columnNames = { "ID", "Username", "Is Admin" };
        Object[][] data;

        try {
            List<User> users = this.mediator.userService.GetUsers(); 

            

        } catch (RemoteException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(loginFrame, "Error fetching users", "Error", JOptionPane.ERROR_MESSAGE);
        }

        loginFrame.getContentPane().removeAll();
        loginFrame.add(adminPanel);
        loginFrame.revalidate();
        loginFrame.repaint();
    }

    @Override
    public void Init() {
        this.showLogin();
    }

    @Override
    public void Close() {
        this.loginFrame.dispose();
    }
}
