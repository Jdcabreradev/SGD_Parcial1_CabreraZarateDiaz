package com.upb.sgd.client.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.rmi.RemoteException;
import java.util.ArrayList;
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
import javax.swing.ListSelectionModel;

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

        // Right panel buttons
        JPanel rightPanel = new JPanel(new GridLayout(2, 2, 50, 50));

        JButton addUserButton = new JButton("Add user");
        rightPanel.add(addUserButton);
        addUserButton.addActionListener(e -> {
            addUser();
        });

        JButton editUserButton = new JButton("Edit user");
        rightPanel.add(editUserButton);
        editUserButton.addActionListener(e -> {
            editUser();
        });

        JButton addGroupButton = new JButton("Add group");
        rightPanel.add(addGroupButton);
        addGroupButton.addActionListener(e -> {
            addGroup();
        });

        JButton editGroupButton = new JButton("Edit group");
        rightPanel.add(editGroupButton);
        editGroupButton.addActionListener(e -> {
            editGroup();
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

    // Pandemonium starts here
    private void addUser() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

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
                
                /* // Create new group with same username
                Group newGroup = new Group();
                newGroup.Name = usernameField.getText();
                mediator.userService.CreateGroup(newGroup.Name);
                int groupId
                
                // Add new user to group
                mediator.userService.CreateUser(newGroup.Id, usernameField.getText(),
                new String(passwordField.getPassword()));
                
                JOptionPane.showMessageDialog(null, "User and group added successfully!"); */
                

                List<Group> allGroups = mediator.userService.GetGroups();
                int newUserGroupId = -1;
                for (Group group : allGroups) {
                    if (group.Name.equals("New Users")) {
                        newUserGroupId = group.Id;
                        break;
                    }

                }

                if (newUserGroupId == -1) {
                    JOptionPane.showMessageDialog(null, "Group 'New Users' not found.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

                try {
                    mediator.userService.CreateUser(newUserGroupId, usernameField.getText(),
                            new String(passwordField.getPassword()));
                } catch (Exception e) {
                    System.err.println("Error creating user: " + e.getMessage());
                }

            } catch (RemoteException e) {
                System.err.println("Error creating user: " + e.getMessage());
            }
        }
    }

    private void editUser() {
        // Get loaded user list
        JScrollPane scrollPane = (JScrollPane) leftPanelContent.getComponent(0);

        JList<String> userJList = (JList<String>) scrollPane.getViewport().getView();
        int selectedIndex = userJList.getSelectedIndex();

        // Check if there's a selected user
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(viewFrame, "Please select a user to edit", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        String selectedUsername = userJList.getSelectedValue();

        try {
            List<User> userList = mediator.userService.GetUsers();
            User selectedUser = null;

            for (User user : userList) {
                if (user.username.equals(selectedUsername)) {
                    selectedUser = user;
                    break;
                }
            }

            if (selectedUser == null) {
                JOptionPane.showMessageDialog(viewFrame, "Selected user not found.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            JTextField usernameField = new JTextField(selectedUser.username);
            JPasswordField passwordField = new JPasswordField();

            List<Group> allGroups = mediator.userService.GetGroups();
            DefaultListModel<String> groupModel = new DefaultListModel<>();
            for (Group group : allGroups) {
                groupModel.addElement(group.Name);
            }
            JList<String> groupList = new JList<>(groupModel);
            groupList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            // Select active groups
            for (int i = 0; i < allGroups.size(); i++) {
                if (selectedUser.groupPermIds.contains(allGroups.get(i).Id)) {
                    groupList.addSelectionInterval(i, i);
                }
            }

            JPanel editUserPanel = new JPanel(new GridLayout(0, 2));
            editUserPanel.add(new JLabel("Username:"));
            editUserPanel.add(usernameField);
            editUserPanel.add(new JLabel("Password (empty to not change)"));
            editUserPanel.add(passwordField);
            editUserPanel.add(new JLabel("Groups:"));
            editUserPanel.add(new JScrollPane(groupList));

            // User edit panel
            int result = JOptionPane.showConfirmDialog(viewFrame, editUserPanel, "Edit User",
                    JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String newUsername = usernameField.getText().trim();
                String newPassword = new String(passwordField.getPassword());

                // Check username
                if (newUsername.isEmpty()) {
                    JOptionPane.showMessageDialog(viewFrame, "Username cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if password is empty
                if (newPassword.isEmpty()) {
                    newPassword = selectedUser.password;
                }

                // Get selected groups
                List<Integer> selectedGroupIds = new ArrayList<>();
                for (int i : groupList.getSelectedIndices()) {
                    selectedGroupIds.add(allGroups.get(i).Id);
                }

                // Update user
                boolean success = mediator.userService.UpdateUser(
                        selectedUser.Id, selectedGroupIds, newUsername, newPassword);

                if (success) {
                    JOptionPane.showMessageDialog(viewFrame, "User updated successfully!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadUserList();
                } else {
                    JOptionPane.showMessageDialog(viewFrame, "Error updating user.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (RemoteException e) {
            System.err.println("Error editing user: " + e.getMessage());
        }
    }

    private void addGroup() {
        JTextField groupNameField = new JTextField();

        JPanel addGroupPanel = new JPanel(new GridLayout(0, 1));
        addGroupPanel.add(new JLabel("Group Name:"));
        addGroupPanel.add(groupNameField);

        int result = JOptionPane.showConfirmDialog(viewFrame, addGroupPanel, "Add new group",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String groupName = groupNameField.getText().trim();

            // Valid group name check
            if (groupName.isEmpty()) {
                JOptionPane.showMessageDialog(viewFrame, "Please enter a valid group name.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                mediator.userService.CreateGroup(groupName);
                JOptionPane.showMessageDialog(viewFrame, "Group added successfully", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                loadGroupList();

            } catch (RemoteException e) {
                JOptionPane.showMessageDialog(viewFrame, "Failed to create group.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }

    private void editGroup() {
        // Obtener la lista de grupos cargados
        JScrollPane scrollPane = (JScrollPane) leftPanelContent.getComponent(0);
        JList<String> groupJList = (JList<String>) scrollPane.getViewport().getView();
        int selectedIndex = groupJList.getSelectedIndex();

        // Comprobar si hay un grupo seleccionado
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(viewFrame, "Please select a group to edit", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String selectedGroupName = groupJList.getSelectedValue();

        try {
            List<Group> groupList = mediator.userService.GetGroups();
            Group selectedGroup = null;

            for (Group group : groupList) {
                if (group.Name.equals(selectedGroupName)) {
                    selectedGroup = group;
                    break;
                }
            }

            if (selectedGroup == null) {
                JOptionPane.showMessageDialog(viewFrame, "Selected group not found.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            JTextField groupNameField = new JTextField(selectedGroup.Name);

            JPanel editGroupPanel = new JPanel(new GridLayout(0, 1));
            editGroupPanel.add(new JLabel("Group Name:"));
            editGroupPanel.add(groupNameField);

            int result = JOptionPane.showConfirmDialog(viewFrame, editGroupPanel, "Edit Group",
                    JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String newGroupName = groupNameField.getText().trim();

                if (newGroupName.isEmpty()) {
                    JOptionPane.showMessageDialog(viewFrame, "Group name cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean success = mediator.userService.UpdateGroup(selectedGroup.Id, newGroupName);

                if (success) {
                    JOptionPane.showMessageDialog(viewFrame, "Group updated successfully!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadGroupList(); // Recargar la lista de grupos
                } else {
                    JOptionPane.showMessageDialog(viewFrame, "Error updating group.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (RemoteException e) {
            System.err.println("Error editing group: " + e.getMessage());
        }
    }

    @Override
    public void Close() {
        this.viewFrame.dispose();
    }
}