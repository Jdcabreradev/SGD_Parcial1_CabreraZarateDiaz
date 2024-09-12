/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.upb.sgd.client.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.upb.sgd.client.GUI.Interface.AbstractGUIWindow;
import com.upb.sgd.client.GUI.Utils.DocumentCellRenderer;
import com.upb.sgd.client.Mediator.ClientMediator;
import com.upb.sgd.shared.domain.DirType;
import com.upb.sgd.shared.domain.Directory;
import com.upb.sgd.shared.domain.Document;
import com.upb.sgd.shared.domain.Folder;

/**
 *
 * @author sebastian
 */
public class FileSystemBrowserWindow extends AbstractGUIWindow {

    private JFrame viewFrame;
    private JTree folderTree;
    private JList<Document> documentList;
    private Folder rootFolder;

    public FileSystemBrowserWindow(ClientMediator mediator) {
        super(mediator);
    }

    @Override
    public void Init() {
        // Create the main frame
        viewFrame = new JFrame("File System Browser");
        viewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewFrame.setSize(800, 600);
        viewFrame.setLayout(new BorderLayout());

        // Toolbar setup
        JToolBar toolBar = new JToolBar();
        JButton fileButton = new JButton("File");
        JMenu fileMenu = new JMenu("File");
        JMenuItem uploadItem = new JMenuItem("Upload");
        uploadItem.addActionListener(e -> {
            // Upload logic here
            System.out.println("Upload clicked");
        });
        fileMenu.add(uploadItem);
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(fileMenu);
        fileButton.addActionListener(e -> popupMenu.show(fileButton, 0, fileButton.getHeight()));
        toolBar.add(fileButton);
        viewFrame.add(toolBar, BorderLayout.NORTH);

        // Left panel setup (folder tree)
        JPanel leftPanel = new JPanel(new BorderLayout());
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(rootFolder);
        folderTree = new JTree(rootNode);
        folderTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) folderTree.getLastSelectedPathComponent();
            if (selectedNode != null) {
                Folder selectedFolder = (Folder) selectedNode.getUserObject();
                updateDocumentList(selectedFolder);
            }
        });
        JScrollPane leftScrollPane = new JScrollPane(folderTree);
        leftPanel.add(leftScrollPane, BorderLayout.CENTER);

        // Bottom search bar with button on left panel
        JPanel leftBottomPanel = new JPanel(new BorderLayout());
        JTextField leftSearchField = new JTextField();
        JButton leftSearchButton = new JButton("Search");
        leftBottomPanel.add(leftSearchField, BorderLayout.CENTER);
        leftBottomPanel.add(leftSearchButton, BorderLayout.EAST);
        leftPanel.add(leftBottomPanel, BorderLayout.SOUTH);

        // Right panel setup (document list)
        JPanel rightPanel = new JPanel(new BorderLayout());
        documentList = new JList<>();
        documentList.setCellRenderer(new DocumentCellRenderer()); // Set custom renderer
        JScrollPane rightScrollPane = new JScrollPane(documentList);
        rightPanel.add(rightScrollPane, BorderLayout.CENTER);

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

        // Load file system data into the view
        loadFileSystemExample();
    }

    @Override
    public void Close() {
        this.viewFrame.dispose();
    }

    // Helper method to generate random size and permissions
    String randomSize() {
        return (100 + (int) (Math.random() * 900)) + " MB";
    }

    String randomPermissions() {
        return "rw-r--r--";
    }

    Date createDate(int year, int month, int day) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(String.format("%d-%02d-%02d", year, month, day));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return new Date(); // Default to current date in case of error
        }
    }

    private void loadFileSystemExample() {
        // Create root folder
        rootFolder = new Folder();
        rootFolder.name = "Root";
        rootFolder.path = "/root";
        rootFolder.dirType = DirType.FILE;
        rootFolder.permissions = randomPermissions();
        rootFolder.size = "0 KB";
        rootFolder.createdAt = createDate(2024, 1, 1);
        rootFolder.updatedAt = new Date();
        rootFolder.tags = new ArrayList<>();

        // Create folders
        Folder folder1 = new Folder();
        folder1.name = "Folder 1";
        folder1.path = "/root/Folder1";
        folder1.dirType = DirType.FILE;
        folder1.permissions = randomPermissions();
        folder1.size = "0 KB";
        folder1.createdAt = createDate(2024, 2, 1);
        folder1.updatedAt = new Date();
        folder1.tags = new ArrayList<>();

        Folder folder2 = new Folder();
        folder2.name = "Folder 2";
        folder2.path = "/root/Folder2";
        folder2.dirType = DirType.FILE;
        folder2.permissions = randomPermissions();
        folder2.size = "0 KB";
        folder2.createdAt = createDate(2024, 3, 1);
        folder2.updatedAt = new Date();
        folder2.tags = new ArrayList<>();

        Folder folder3 = new Folder();
        folder3.name = "Folder 3";
        folder3.path = "/root/Folder3";
        folder3.dirType = DirType.FILE;
        folder3.permissions = randomPermissions();
        folder3.size = "0 KB";
        folder3.createdAt = createDate(2024, 4, 1);
        folder3.updatedAt = new Date();
        folder3.tags = new ArrayList<>();

        // Create files for folders
        Document file1 = new Document();
        file1.name = "File 1-1";
        file1.path = "/root/Folder1/File1-1";
        file1.dirType = DirType.FILE;
        file1.permissions = randomPermissions();
        file1.size = randomSize();
        file1.createdAt = createDate(2024, 2, 10);
        file1.updatedAt = new Date();
        file1.tags = new ArrayList<>();

        Document file2 = new Document();
        file2.name = "File 2-1";
        file2.path = "/root/Folder2/File2-1";
        file2.dirType = DirType.FILE;
        file2.permissions = randomPermissions();
        file2.size = randomSize();
        file2.createdAt = createDate(2024, 3, 10);
        file2.updatedAt = new Date();
        file2.tags = new ArrayList<>();

        Document file3 = new Document();
        file3.name = "File 3-1";
        file3.path = "/root/Folder3/File3-1";
        file3.dirType = DirType.FILE;
        file3.permissions = randomPermissions();
        file3.size = randomSize();
        file3.createdAt = createDate(2024, 4, 10);
        file3.updatedAt = new Date();
        file3.tags = new ArrayList<>();

        Document file4 = new Document();
        file4.name = "File 3-2";
        file4.path = "/root/Folder3/File3-2";
        file4.dirType = DirType.FILE;
        file4.permissions = randomPermissions();
        file4.size = randomSize();
        file4.createdAt = createDate(2024, 4, 15);
        file4.updatedAt = new Date();
        file4.tags = new ArrayList<>();

        Document file5 = new Document();
        file5.name = "File 3-3";
        file5.path = "/root/Folder3/File3-3";
        file5.dirType = DirType.FILE;
        file5.permissions = randomPermissions();
        file5.size = randomSize();
        file5.createdAt = createDate(2024, 4, 5);
        file5.updatedAt = new Date();
        file5.tags = new ArrayList<>();

        // Create a nested folder
        Folder nestedFolder = new Folder();
        nestedFolder.name = "Nested Folder";
        nestedFolder.path = "/root/Folder3/NestedFolder";
        nestedFolder.dirType = DirType.FILE;
        nestedFolder.permissions = randomPermissions();
        nestedFolder.size = "0 KB";
        nestedFolder.createdAt = createDate(2024, 4, 20);
        nestedFolder.updatedAt = new Date();
        nestedFolder.tags = new ArrayList<>();
        nestedFolder.AddDirectory(file4); // Add file to the nested folder

        // Insert nested folder into folder 3
        folder3.AddDirectory(nestedFolder);
        folder3.AddDirectory(file3); // Add file to folder 3
        folder3.AddDirectory(file5);

        // Insert files into folders
        folder1.AddDirectory(file1);
        folder2.AddDirectory(file2);

        // Insert folders into root
        rootFolder.AddDirectory(folder1);
        rootFolder.AddDirectory(folder2);
        rootFolder.AddDirectory(folder3);

        // Update GUI components with the file system data
        updateFolderTree(rootFolder);
        updateDocumentList(rootFolder);
    }

    private void updateFolderTree(Folder folder) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(folder);
        populateFolderTree(rootNode, folder);
        folderTree.setModel(new DefaultTreeModel(rootNode));
    }

    private void populateFolderTree(DefaultMutableTreeNode parentNode, Folder folder) {
        for (Directory child : folder.children) {
            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
            parentNode.add(childNode);
            if (child instanceof Folder tmpFolder) {
                populateFolderTree(childNode, tmpFolder);
            }
        }
    }

    private void updateDocumentList(Folder folder) {
        DefaultListModel<Document> listModel = new DefaultListModel<>();
        if (folder != null) {
            for (Directory dir : folder.children) {
                if (dir instanceof Document document) {
                    listModel.addElement(document);
                }
            }
        }
        documentList.setModel(listModel);
    }
}
