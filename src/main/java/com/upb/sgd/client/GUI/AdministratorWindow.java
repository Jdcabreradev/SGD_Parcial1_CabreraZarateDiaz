package com.upb.sgd.client.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.upb.sgd.client.GUI.Interface.AbstractGUIWindow;
import com.upb.sgd.client.Mediator.ClientMediator;
import com.upb.sgd.shared.domain.Group;
import com.upb.sgd.shared.domain.User;

/**
 *
 * @author jj
 */
public class AdministratorWindow extends AbstractGUIWindow {
    private JFrame viewFrame;
    private JPanel leftPanelContent;

    public AdministratorWindow(ClientMediator mediator) {
        super(mediator);
    }

    @Override
    public void Init() {
        viewFrame = new JFrame("Administrator Panel");
        viewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewFrame.setSize(800, 600);
        viewFrame.setLayout(new BorderLayout());

        JToolBar toolBar = new JToolBar();
        JButton fileButton = new JButton("File");
        JMenu fileMenu = new JMenu("File");
        JMenuItem uploadItem = new JMenuItem("Upload");

        uploadItem.addActionListener(e -> {
            System.out.println("Upload clicked");
        });

        fileMenu.add(uploadItem);
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(fileMenu);
        fileButton.addActionListener(e -> popupMenu.show(fileButton, 0, fileButton.getHeight()));
        toolBar.add(fileButton);
        viewFrame.add(toolBar, BorderLayout.NORTH);

        // Left panel setup
        JPanel leftPanel = new JPanel(new BorderLayout());
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            restoreInitialView();
        });
        leftPanel.add(backButton, BorderLayout.NORTH);

        // Left panel content set up
        leftPanelContent = new JPanel(new GridLayout(0, 1, 20, 20));
        leftPanel.add(leftPanelContent, BorderLayout.CENTER);

        // User list button
        JButton userListButton = new JButton("Manage users");
        userListButton.addActionListener(e -> {
            loadUserList();
        });
        leftPanelContent.add(userListButton);

        // Group list button
        JButton groupListButton = new JButton("Manage groups");
        groupListButton.addActionListener(e -> {
            loadGroupList();
        });
        leftPanelContent.add(groupListButton);

        // Bottom search bar with button on left panel
        JPanel leftBottomPanel = new JPanel(new BorderLayout());
        JTextField leftSearchField = new JTextField();
        JButton leftSearchButton = new JButton("Search");
        leftBottomPanel.add(leftSearchField, BorderLayout.CENTER);
        leftBottomPanel.add(leftSearchButton, BorderLayout.EAST);
        leftPanel.add(leftBottomPanel, BorderLayout.SOUTH);

        // Right panel setup (empty for now)
        JPanel rightPanel = new JPanel(new GridLayout(2, 2, 50, 50));

        JButton addUserButton = new JButton("Add user");
        rightPanel.add(addUserButton);
        addUserButton.addActionListener(e -> {
            addUser();
        });

        JButton editUserButton = new JButton("Edit user");
        rightPanel.add(editUserButton);
        editUserButton.addActionListener(e -> {
            // editUser();
        });

        JButton addGroupButton = new JButton("Add group");
        rightPanel.add(addGroupButton);
        addGroupButton.addActionListener(e -> {
            addGroup();
        });

        JButton editGroupButton = new JButton("Edit group");
        rightPanel.add(editGroupButton);
        editGroupButton.addActionListener(e -> {
            // editGroup();
        });

        // Split pane to separate left and right panels
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        viewFrame.add(splitPane, BorderLayout.CENTER);

        // Footer setup (Placeholder for notifications)
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel notificationLabel = new JLabel("Notifications will be displayed here.");
        footerPanel.add(notificationLabel);
        viewFrame.add(footerPanel, BorderLayout.SOUTH);

        // Show the frame
        viewFrame.setVisible(true);
    }

    private void loadUserList() {
        try {
            List<User> userList = this.mediator.userService.GetUsers();

            // Adds users from the database into the JList
            DefaultListModel<String> userModel = new DefaultListModel<>();
            for (User user : userList) {
                userModel.addElement(user.username);
            }
            JList<String> userJList = new JList<>(userModel);

            // Removes all content in panel and loads the list
            leftPanelContent.removeAll();
            leftPanelContent.add(new JScrollPane(userJList));

            leftPanelContent.revalidate();
            leftPanelContent.repaint();

        } catch (RemoteException e) {
            System.err.println("Error fetching users: " + e.getMessage());
        }
    }

    private void loadGroupList() {
        try {
            List<Group> groupList = this.mediator.userService.GetGroups();
            DefaultListModel<String> groupModel = new DefaultListModel<>();
            for (Group group : groupList) {
                groupModel.addElement(group.Name);
            }
            JList<String> groupJList = new JList<>(groupModel);

            leftPanelContent.removeAll();
            leftPanelContent.add(new JScrollPane(groupJList));

            leftPanelContent.revalidate();
            leftPanelContent.repaint();

        } catch (RemoteException e) {
            System.err.println("Error fetching groups: " + e.getMessage());
        }
    }

    private void restoreInitialView() {
        leftPanelContent.removeAll(); // Clean current content
        JButton userListButton = new JButton("Manage users");
        userListButton.addActionListener(e -> {
            loadUserList();
        });

        JButton groupListButton = new JButton("Manage groups");
        groupListButton.addActionListener(e -> {
            loadGroupList();
        });

        // Restore original buttons
        leftPanelContent.add(userListButton);
        leftPanelContent.add(groupListButton);
        leftPanelContent.revalidate();
        leftPanelContent.repaint();
    }

    private void addUser() {  // Doesn't work
        JTextField usernameField = new JTextField(10);
        JPasswordField passwordField = new JPasswordField(10);

        JPanel addUserPanel = new JPanel();
        addUserPanel.setLayout(new GridLayout(3, 2));
        addUserPanel.add(new JLabel("Username:"));
        addUserPanel.add(usernameField);
        addUserPanel.add(new JLabel("Password:"));
        addUserPanel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(null, addUserPanel,
                        "Please Enter User Details", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                this.mediator.userService.CreateUser(1, usernameField.getText(), new String(passwordField.getPassword()));
            } catch (RemoteException e) {
                System.err.println("Error creating user: " + e.getMessage());
            }
        }
    }

    private void editUser() {

    }

    private void addGroup() {
        JTextField groupNameField = new JTextField();
    
        JPanel addGroupPanel = new JPanel(new GridLayout(0, 1));
        addGroupPanel.add(new JLabel("Group Name:"));
        addGroupPanel.add(groupNameField);
    
        int result = JOptionPane.showConfirmDialog(viewFrame, addGroupPanel, "Add new group", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String groupName = groupNameField.getText().trim();
    
            // Valid group name check
            if (groupName.isEmpty()) {
                JOptionPane.showMessageDialog(viewFrame, "Please enter a valid group name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            try {
                mediator.userService.CreateGroup(groupName);
                JOptionPane.showMessageDialog(viewFrame, "Group added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadGroupList();
    
            } catch (RemoteException e) {
                JOptionPane.showMessageDialog(viewFrame, "Error adding group: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editGroup() {

    }

    @Override
    public void Close() {
        this.viewFrame.dispose();
    }
}